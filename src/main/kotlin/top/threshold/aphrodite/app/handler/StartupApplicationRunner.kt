package top.threshold.aphrodite.app.handler

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import top.threshold.aphrodite.pkg.constant.CacheKey
import top.threshold.aphrodite.pkg.model.Slf4j
import top.threshold.aphrodite.pkg.model.Slf4j.Companion.log
import top.threshold.aphrodite.pkg.utils.RedisUtil


@Slf4j
@Component
class StartupApplicationRunner(
    val redisUtil: RedisUtil,
    @Value("\${server.address:127.0.0.1}")
    val address: String,
    @Value("\${server.port:8000}")
    val port: String
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        if (!redisUtil.hasKey(CacheKey.NEXT_UNO)) {
            redisUtil.setLong(CacheKey.NEXT_UNO, 100000)
        }
        log.info("===============================")
        log.info("Listening on {\"host\": \"http://$address:$port\"}")
        log.info("Docs addr {\"addr\": \"http://$address:$port/swagger-ui/index.html\"}")
        log.info("===============================")
    }
}
