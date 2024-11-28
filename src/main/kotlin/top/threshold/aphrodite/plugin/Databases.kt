package top.threshold.aphrodite.plugin

import io.ktor.server.application.*
import top.threshold.aphrodite.common.DatabaseUtil
import top.threshold.aphrodite.common.RedisUtil

fun Application.configureDatabases() {
    RedisUtil.init(this)
    DatabaseUtil.init(this)
}
