package top.threshold.aphrodite.routes

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import top.threshold.aphrodite.common.KtResult
import top.threshold.aphrodite.services.UserService


fun Route.authRoutesV1() {
    val userService by inject<UserService>()

    route("/send-code") {
        post {
            val loginRequest = call.receive<LoginRequest>()
            call.respond(KtResult.ok<Unit>())
        }
    }

    route("/login") {
        post {
            call.respondText("Create a new user", status = HttpStatusCode.Created)
        }
    }

    route("/logout") {
        post {
            call.respondText("Create a new user", status = HttpStatusCode.Created)
        }
    }
}

data class LoginRequest(
    val phone: String
) {
    fun isValidPhoneNumber(): Boolean {
        val phoneRegex = "^\\+86\\s?1[3-9]\\d{9}\$".toRegex()
        return phoneRegex.matches(phone)
    }
}

