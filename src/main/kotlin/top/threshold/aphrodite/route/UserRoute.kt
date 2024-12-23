package top.threshold.aphrodite.route

import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import top.threshold.aphrodite.common.ErrorCode
import top.threshold.aphrodite.common.R
import top.threshold.aphrodite.plugin.getLoginUser
import top.threshold.aphrodite.service.UserService
import top.threshold.aphrodite.utils.DatabaseUtil

fun Route.userRoutesV1() {
    val userService = UserService(DatabaseUtil.getDatabase())

    route("/{id}") {
        get {
            val userCode = call.getLoginUser()
            val userSchema = userService.getByCode(userCode)
            call.respond(
                R.ok(
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
        userSchema ?: call.respond(R.err<Unit>(ErrorCode.ERR_DATA))
        val updateUserRequest = call.receive<UpdateUserRequest>()
        userService.update(
            userSchema!!.copy(
                email = updateUserRequest.email,
                nickname = updateUserRequest.nickname,
                updatedBy = "777",
                updatedAt = Clock.System.now()
            )
        )
        call.respond(R.ok<Unit>())
    }

    delete {
        userService.delete(call.getLoginUser())
        call.respond(R.ok<Unit>())
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
