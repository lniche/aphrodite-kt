package top.threshold.aphrodite.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import top.threshold.aphrodite.common.*
import top.threshold.aphrodite.models.UserSchema
import top.threshold.aphrodite.plugins.getLoginUser
import top.threshold.aphrodite.services.UserService


fun Route.authRoutesV1() {
    val userService = UserService(DatabaseUtil.getDatabase())

    route("/send-code") {
        post {
            val sendVerifyCodeRequest = call.receive<SendVerifyCodeRequest>()
            val codeKey = CacheKey.SMS_CODE + sendVerifyCodeRequest.phone
            if (RedisUtil.getRedisCommands().exists(codeKey) > 0) {
                call.respond(KtResult.err<Unit>(message = "Verification code is incorrect, please re-enter"))
            }
            val cacheCode = (1000..9999).random().toString()
            application.log.info("cache code: $cacheCode")
            RedisUtil.getRedisCommands().set(codeKey, cacheCode)
            RedisUtil.getRedisCommands().expire(codeKey, 60)
            // TODO: fake send
            call.respond(KtResult.ok<Unit>())
        }
    }

    route("/login") {
        post {
            val loginRequest = call.receive<LoginRequest>()
            val codeKey = CacheKey.SMS_CODE + loginRequest.phone
            if (loginRequest.code != RedisUtil.getRedisCommands().get(codeKey)) {
                call.respond(KtResult.err<Unit>("Verification code is incorrect, please re-enter"))
            }
            var userSchema = userService.getByPhone(loginRequest.phone)
            if (userSchema == null) {
                userSchema = UserSchema(
                    phone = loginRequest.phone,
                    userCode = Snowflake(1, 1).generateId().toString(),
                    userNo = RedisUtil.nextId(CacheKey.NEXT_UNO),
                    loginAt = Clock.System.now(),
                    loginToken = "",
                    clientIp = call.request.host(),
                    nickname = "SUGAR_" + loginRequest.phone.takeLast(4)
                )
                userService.create(
                    userSchema
                )
            } else {
                userService.update(
                    userSchema.copy(
                        clientIp = call.request.host(),
                        loginAt = Clock.System.now(),
                        loginToken = "",
                    )
                )
            }
            RedisUtil.getRedisCommands().del(codeKey)
            call.respond(
                KtResult.ok(
                    LoginResponse(
                        accessToken = userSchema.loginToken!!
                    )
                )
            )
        }
    }

    route("/logout") {
        post {
            val userCode = call.getLoginUser()
            call.respondText("Create a new user", status = HttpStatusCode.Created)
        }
    }
}

@Serializable
data class SendVerifyCodeRequest(
    val phone: String
)

@Serializable
data class LoginRequest(
    val phone: String,
    val code: String
)

@Serializable
data class LoginResponse(
    val accessToken: String
)
