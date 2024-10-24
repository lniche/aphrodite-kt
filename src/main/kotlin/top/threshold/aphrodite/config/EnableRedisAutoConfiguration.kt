package top.threshold.aphrodite.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import top.threshold.aphrodite.helper.HuJsonRedisSerializer


@Configuration
class EnableRedisAutoConfiguration {
    val huJsonRedisSerializer = HuJsonRedisSerializer()

    @Bean
    fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = factory
        val stringRedisSerializer = StringRedisSerializer()
        // key采用String的序列化方式
        redisTemplate.keySerializer = stringRedisSerializer
        // hash的key也采用String的序列化方式
        redisTemplate.hashKeySerializer = stringRedisSerializer
        // value序列化方式采用hutool
        redisTemplate.valueSerializer = huJsonRedisSerializer
        // hash的value序列化方式采用hutool
        redisTemplate.hashValueSerializer = huJsonRedisSerializer
        // 初始化 RedisTemplate 序列化完成
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