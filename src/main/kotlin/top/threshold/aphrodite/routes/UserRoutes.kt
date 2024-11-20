package top.threshold.aphrodite.routes

import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import top.threshold.aphrodite.common.DatabaseUtil
import top.threshold.aphrodite.common.ErrorCode
import top.threshold.aphrodite.common.KtResult
import top.threshold.aphrodite.plugins.getLoginUser
import top.threshold.aphrodite.services.UserService

fun Route.userRoutesV1() {
    val userService = UserService(DatabaseUtil.getDatabase())

    route("/{id}") {
        get {
            val userCode = call.getLoginUser()
            val userSchema = userService.getByCode(userCode)
            call.respond(
                KtResult.ok(
                    GetUserResponse(
                        userCode = userSchema?.userCode,
                        userNo = userSchema?.userNo,
                        nickname = userSchema?.nickname,
                        email = userSchema?.email,
                        phone = userSchema?.phone
                    )
                )
            )
        }
    }

    put {
        val userCode = call.getLoginUser()
        val userSchema = userService.getByCode(userCode)
        userSchema ?: call.respond(KtResult.err<Unit>(ErrorCode.ERR_DATA))
        val updateUserRequest = call.receive<UpdateUserRequest>()
        userService.update(
            userSchema!!.copy(
                email = updateUserRequest.email,
                nickname = updateUserRequest.nickname
            )
        )
        call.respond(KtResult.ok<Unit>())
    }

    delete {
        userService.delete(call.getLoginUser())
        call.respond(KtResult.ok<Unit>())
    }
}

@Serializable
data class UpdateUserRequest(
    val nickname: String? = null,
    val email: String? = null
)

@Serializable
data class GetUserResponse(
    val nickname: String? = null,
    val userNo: Long? = null,
    val userCode: String? = null,
    val email: String? = null,
    val phone: String? = null
)
