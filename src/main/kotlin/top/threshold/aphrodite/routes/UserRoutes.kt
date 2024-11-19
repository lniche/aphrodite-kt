package top.threshold.aphrodite.routes

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Route.userRoutesV1() {

    put {
        call.respondText("Create a new user", status = HttpStatusCode.Created)
    }

    delete {
        call.respondText("Create a new user", status = HttpStatusCode.Created)
    }


    route("/{id}") {
        get {
            val userId = call.parameters["id"]
            call.respondText("User details for id: $userId", status = HttpStatusCode.OK)
        }
    }
}

@Serializable
class UpdateUserRequest {
    var nickname: String? = null
    var email: String? = null
}

@Serializable
class GetUserResponse {
    var nickname: String? = null
    var userNo: Long? = null
    var userCode: String? = null
    var email: String? = null
    var phone: String? = null
}
