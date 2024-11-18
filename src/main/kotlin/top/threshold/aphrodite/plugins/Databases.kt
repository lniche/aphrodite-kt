package top.threshold.aphrodite.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import top.threshold.aphrodite.services.UserService

fun Application.configureDatabases() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single { connectToPostgres() }
            single { UserService(get()) }
        })
    }
}

fun Application.connectToPostgres(): Database {
    Class.forName("org.postgresql.Driver")
    val url = environment.config.property("postgres.url").getString()
    val user = environment.config.property("postgres.user").getString()
    val password = environment.config.property("postgres.password").getString()

    return Database.connect(
        url = url,
        driver = "org.postgresql.Driver",
        user = user,
        password = password
    )
}
