package top.threshold.aphrodite.app.handler

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import top.threshold.aphrodite.pkg.constant.CacheKey
import top.threshold.aphrodite.pkg.entity.Slf4j
import top.threshold.aphrodite.pkg.entity.Slf4j.Companion.log
import top.threshold.aphrodite.pkg.util.RedisUtil


@Slf4j
@Component
class StartupApplicationRunner(
    val redisUtil: RedisUtil,
    @Value("\${spring.profiles.active:dev}")
    val active: String
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        if (!redisUtil.hasKey(CacheKey.NEXT_UNO)) {
            redisUtil.setLong(CacheKey.NEXT_UNO, 100000)
        }
        if ("prod" != active) {
            log.info("===============================")
            log.info("Listening on {\"host\": \"http://127.0.0.1:8000\"}")
            log.info("Docs addr {\"addr\": \"http://127.0.0.1:8000/swagger-ui/index.html\"}")
            log.info("===============================")
        }
    }
}