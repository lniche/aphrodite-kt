package top.threshold.aphrodite.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import top.threshold.aphrodite.common.KtResult
import top.threshold.aphrodite.routes.authRoutesV1
import top.threshold.aphrodite.routes.userRoutesV1

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        get("/") {
            call.respondText("Thank you for using Aphrodite!")
        }
        get("/ping") {
            call.respond(KtResult.ok("pong"))
        }
        route("/v1") {
            route("/user") {
                userRoutesV1()
            }
            authRoutesV1()
        }
    }
}
