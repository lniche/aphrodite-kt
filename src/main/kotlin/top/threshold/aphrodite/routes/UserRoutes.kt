package top.threshold.aphrodite.routes

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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
