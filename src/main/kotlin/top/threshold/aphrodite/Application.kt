package top.threshold.aphrodite

import io.ktor.server.application.*
import io.ktor.server.netty.*
import top.threshold.aphrodite.plugins.*
import java.util.*

fun main(args: Array<String>) {
    System.setProperty("user.timezone", "UTC")
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
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
