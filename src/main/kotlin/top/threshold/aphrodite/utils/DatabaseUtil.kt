package top.threshold.aphrodite.utils

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

object DatabaseUtil {
    private lateinit var database: Database

    fun init(application: Application) {
        val databaseConfig = application.environment.config.config("postgres")
        val url = databaseConfig.property("url").getString()
        val user = databaseConfig.property("user").getString()
        val password = databaseConfig.property("password").getString()

        database = Database.connect(
            url = url,
            driver = "org.postgresql.Driver",
            user = user,
            password = password
        )
    }

    fun getDatabase(): Database {
        return database
    }

}
