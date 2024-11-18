package top.threshold.aphrodite.routes

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import top.threshold.aphrodite.common.KtResult

fun Route.authRoutesV1() {
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

