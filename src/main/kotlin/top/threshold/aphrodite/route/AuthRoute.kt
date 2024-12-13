package top.threshold.aphrodite.route

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import top.threshold.aphrodite.common.CacheKey
import top.threshold.aphrodite.common.R
import top.threshold.aphrodite.model.UserSchema
import top.threshold.aphrodite.plugin.generateJWT
import top.threshold.aphrodite.plugin.getLoginUser
import top.threshold.aphrodite.service.UserService
import top.threshold.aphrodite.utils.DatabaseUtil
import top.threshold.aphrodite.utils.RedisUtil
import top.threshold.aphrodite.utils.Snowflake


fun Route.authRoutesV1() {
    val userService = UserService(DatabaseUtil.getDatabase())

    route("/send-code") {
        post {
            val sendVerifyCodeRequest = call.receive<SendVerifyCodeRequest>()
            val codeKey = CacheKey.SMS_CODE + sendVerifyCodeRequest.phone
            if (RedisUtil.getRedisCommands().exists(codeKey) > 0) {
                call.respond(R.err<Unit>(message = "Verification code is incorrect, please re-enter"))
            }
            val cacheCode = (1000..9999).random().toString()
            application.log.info("cache code: $cacheCode")
            RedisUtil.getRedisCommands().set(codeKey, cacheCode)
            RedisUtil.getRedisCommands().expire(codeKey, 60)
            // TODO: fake send
            call.respond(R.ok<Unit>())
        }
    }

    route("/login") {
        post {
            val loginRequest = call.receive<LoginRequest>()
            val codeKey = CacheKey.SMS_CODE + loginRequest.phone
            if (loginRequest.code != RedisUtil.getRedisCommands().get(codeKey)) {
                call.respond(R.err<Unit>("Verification code is incorrect, please re-enter"))
            }
            var userSchema = userService.getByPhone(loginRequest.phone)
            if (userSchema == null) {
                val userCode = Snowflake(1, 1).generateId().toString()
                userSchema = UserSchema(
                    phone = loginRequest.phone,
                    userCode = userCode,
                    userNo = RedisUtil.nextId(CacheKey.NEXT_UNO),
                    loginAt = Clock.System.now(),
                    loginToken = call.generateJWT(userCode),
                    clientIp = call.request.host(),
                    nickname = "SUGAR_" + loginRequest.phone.takeLast(4),
                    createdBy = "777",
                    createdAt = Clock.System.now()
                )
                userService.create(
                    userSchema
                )
            } else {
                userService.update(
                    userSchema.copy(
                        clientIp = call.request.host(),
                        loginAt = Clock.System.now(),
                        loginToken = call.generateJWT(userSchema.userCode),
                        updatedBy = "777",
                        updatedAt = Clock.System.now()
                    )
                )
            }
            RedisUtil.getRedisCommands().del(codeKey)
            call.respond(
                R.ok(
                    LoginResponse(
                        accessToken = userSchema.loginToken!!
                    )
                )
            )
        }
    }

    route("/logout") {
        post {
            userService.logout(call.getLoginUser())
            call.respond(R.ok<Unit>())
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
