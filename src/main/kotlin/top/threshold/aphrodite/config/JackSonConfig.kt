package top.threshold.aphrodite.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.deser.std.DateDeserializers
import com.fasterxml.jackson.databind.ser.std.DateSerializer
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import lombok.SneakyThrows
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Configuration
class JackSonConfig {
    @Bean
    fun ObjectMapper(): ObjectMapper {
        val objectMapper = com.fasterxml.jackson.databind.ObjectMapper()
        val javaTimeModule = JavaTimeModule()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        // 序列化
        javaTimeModule.addSerializer(
            Long::class.javaObjectType,
            ToStringSerializer.instance
        )
        javaTimeModule.addSerializer(
            LocalDate::class.java,
            LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        )
        javaTimeModule.addSerializer(
            LocalTime::class.java,
            LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss"))
        )
        javaTimeModule.addSerializer(
            Date::class.java,
            DateSerializer(false, SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
        )

        // 反序列化
        javaTimeModule.addDeserializer(
            LocalDate::class.java,
            LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        )
        javaTimeModule.addDeserializer(
            LocalTime::class.java,
            LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss"))
        )
        javaTimeModule.addDeserializer(Date::class.java, object : DateDeserializers.DateDeserializer() {
            @SneakyThrows
            override fun deserialize(jsonParser: JsonParser, dc: DeserializationContext): Date {
                val text = jsonParser.text.trim { it <= ' ' }
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                return sdf.parse(text)
            }
        })
        objectMapper.registerModule(javaTimeModule)
        objectMapper.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        return objectMapper
    }
}