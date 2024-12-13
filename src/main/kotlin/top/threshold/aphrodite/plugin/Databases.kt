package top.threshold.aphrodite.plugin

import io.ktor.server.application.*
import top.threshold.aphrodite.utils.DatabaseUtil
import top.threshold.aphrodite.utils.RedisUtil

fun Application.configureDatabases() {
    RedisUtil.init(this)
    DatabaseUtil.init(this)
}
