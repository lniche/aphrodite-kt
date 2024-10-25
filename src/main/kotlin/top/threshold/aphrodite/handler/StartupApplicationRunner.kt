package top.threshold.aphrodite.handler

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import top.threshold.aphrodite.constant.CacheKey
import top.threshold.aphrodite.entity.Slf4j
import top.threshold.aphrodite.entity.Slf4j.Companion.log
import top.threshold.aphrodite.utils.RedisUtil


@Slf4j
@Component
class StartupApplicationRunner(
    val redisUtil: RedisUtil,
    @Value("\${spring.profiles.active:dev}")
    val active: String
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        // 初始化序列号
        if (!redisUtil.hasKey(CacheKey.NEXTID_UNO)) {
            redisUtil.setLong(CacheKey.NEXTID_UNO, 100000)
        }
        if ("dev" == active) {
            log.info("server start {\"host\": \"http://127.0.0.1:8000\"}")
            log.info("docs addr {\"addr\": \"http://127.0.0.1:8000/swagger-ui/index.html\"}")
        }
    }
}
