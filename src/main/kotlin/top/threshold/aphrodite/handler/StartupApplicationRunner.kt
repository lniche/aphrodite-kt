package top.threshold.aphrodite.handler

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import top.threshold.aphrodite.constant.CacheKey
import top.threshold.aphrodite.util.RedisUtil

@Component
class StartupApplicationRunner(
    val redisUtil: RedisUtil
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        if (!redisUtil.hasKey(CacheKey.NEXTID_UNO)) {
            redisUtil.setLong(CacheKey.NEXTID_UNO, 10000000)
        }
    }
}
