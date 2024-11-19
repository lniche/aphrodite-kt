package top.threshold.aphrodite.common

import io.ktor.server.application.*
import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.sync.RedisCommands

object RedisUtil {
    private lateinit var connection: StatefulRedisConnection<String, String>
    private lateinit var redisCommands: RedisCommands<String, String>
    private lateinit var redisClient: RedisClient

    fun init(application: Application) {
        val redisConfig = application.environment.config.config("redis")
        val host = redisConfig.property("host").getString()
        val port = redisConfig.property("port").getString()
        val db = redisConfig.property("db").getString().toInt()
        redisClient = RedisClient.create("redis://$host:$port/$db")
        connection = redisClient.connect()
        redisCommands = connection.sync()
    }

    fun getRedisCommands(): RedisCommands<String, String> {
        return redisCommands
    }

    fun nextId(key: String): Long {
        return redisCommands.incr(key)
    }

    fun close() {
        connection.close()
        redisClient.shutdown()
    }
}
