package top.threshold.aphrodite.plugins

import io.ktor.server.application.*
import io.lettuce.core.RedisClient
import io.lettuce.core.api.sync.RedisCommands
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    val connectToPostgres = connectToPostgres()
    val connectToRedis = connectToRedis()
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

fun Application.connectToRedis(): RedisCommands<String, String> {
    val host = environment.config.property("redis.host").getString()
    val port = environment.config.property("redis.port").getString()
    val db = environment.config.property("redis.db").getString().toInt()
    val redisClient = RedisClient.create("redis://$host:$port/$db")
    val statefulRedisConnection = redisClient.connect()
    val redisCommands = statefulRedisConnection.sync()
    environment.monitor.subscribe(ApplicationStopped) {
        log.info("Shutting down Redis connection and client.")
        statefulRedisConnection.close()
        redisClient.shutdown()
    }
    return redisCommands
}
