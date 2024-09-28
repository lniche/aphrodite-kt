package top.threshold.ktscaffold.config

import com.yingwu.rainbow.infrastructure.utils.ThreadPoolExecutorMdcWrapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.CustomizableThreadFactory
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy
import java.util.concurrent.TimeUnit

@Configuration
@EnableAsync
class AsyncThreadPoolConfig {
    @Value("\${threadpool.core-pool-size:4}")
    private val corePoolSize = 0

    @Value("\${threadpool.max-pool-size:8}")
    private val maxPoolSize = 0

    @Value("\${threadpool.queue-capacity:1000}")
    private val queueCapacity = 0

    @Value("\${threadpool.keep-alive-seconds:3000}")
    private val keepAliveSeconds = 0

    /**
     * 公平队列
     */
    @Bean("asyncFairExecutor")
    fun asyncFairExecutor(): Executor {
        return ThreadPoolExecutorMdcWrapper(
            corePoolSize,
            maxPoolSize,
            keepAliveSeconds.toLong(),
            TimeUnit.MILLISECONDS,
            ArrayBlockingQueue(queueCapacity, true),
            CustomizableThreadFactory("asyncFairExecutor-"),
            CallerRunsPolicy()
        )
    }

    /**
     * 非公平队列
     */
    @Bean("asyncNoFairExecutor")
    fun asyncNoFairExecutor(): Executor {
        return ThreadPoolExecutorMdcWrapper(
            corePoolSize,
            maxPoolSize,
            keepAliveSeconds.toLong(),
            TimeUnit.MILLISECONDS,
            ArrayBlockingQueue(queueCapacity, true),
            CustomizableThreadFactory("asyncNoFairExecutor-"),
            CallerRunsPolicy()
        )
    }
}