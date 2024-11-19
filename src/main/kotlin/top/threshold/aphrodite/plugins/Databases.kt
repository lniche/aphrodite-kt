package top.threshold.aphrodite.plugins

import io.ktor.server.application.*
import top.threshold.aphrodite.common.DatabaseUtil
import top.threshold.aphrodite.common.RedisUtil

fun Application.configureDatabases() {
    RedisUtil.init(this)
    DatabaseUtil.init(this)
}
