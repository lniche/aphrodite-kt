package top.threshold.aphrodite.routes

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutesV1() {
    route("/send-code") {
        post {
            call.respondText("Create a new user", status = HttpStatusCode.Created)
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
