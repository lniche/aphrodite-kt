package top.threshold.aphrodite.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import top.threshold.aphrodite.pkg.helper.HuJsonRedisSerializer


@Configuration
class EnableRedisAutoConfiguration {
    val huJsonRedisSerializer = HuJsonRedisSerializer()

    @Bean
    fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = factory
        val stringRedisSerializer = StringRedisSerializer()
        redisTemplate.keySerializer = stringRedisSerializer
        redisTemplate.hashKeySerializer = stringRedisSerializer
        redisTemplate.valueSerializer = huJsonRedisSerializer
        redisTemplate.hashValueSerializer = huJsonRedisSerializer
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }

    @Bean
    fun stringRedisTemplate(redisConnectionFactory: RedisConnectionFactory?): StringRedisTemplate {
        val template = StringRedisTemplate()
        template.connectionFactory = redisConnectionFactory!!
        return template
    }
}