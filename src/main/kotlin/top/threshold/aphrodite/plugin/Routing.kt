package top.threshold.aphrodite.plugin

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import top.threshold.aphrodite.common.KtResult
import top.threshold.aphrodite.route.authRoutesV1
import top.threshold.aphrodite.route.userRoutesV1

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.application.log.error("Exception:", cause)
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }

    routing {
        swaggerUI(path = "/swagger", swaggerFile = "openapi/documentation.json")
        openAPI(path = "/openapi", swaggerFile = "openapi/documentation.json")

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
