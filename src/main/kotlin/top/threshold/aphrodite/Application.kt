package top.threshold.aphrodite

import io.ktor.server.application.*
import io.ktor.server.netty.*
import top.threshold.aphrodite.plugins.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureHTTP()
    configureMonitoring()
    configureAdministration()
    configureSerialization()
    configureDatabases()
    configureSecurity()
    configureRouting()
}
