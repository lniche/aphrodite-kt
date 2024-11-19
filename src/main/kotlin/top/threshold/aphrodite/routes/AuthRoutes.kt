package top.threshold.aphrodite.routes

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import top.threshold.aphrodite.common.CacheKey
import top.threshold.aphrodite.common.KtResult


fun Route.authRoutesV1() {

    route("/send-code") {
        post {
            val loginRequest = call.receive<LoginRequest>()
            val redisKey = CacheKey.SMS_CODE + loginRequest.phone
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

@Serializable
data class LoginRequest(
    val phone: String
)

