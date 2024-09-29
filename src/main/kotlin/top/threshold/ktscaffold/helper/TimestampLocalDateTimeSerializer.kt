package top.threshold.ktscaffold.helper

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneOffset

class TimestampLocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {
    @Throws(IOException::class)
    override fun serialize(
        localDateTime: LocalDateTime,
        jsonGenerator: JsonGenerator,
        serializerProvider: SerializerProvider
    ) {
        jsonGenerator.writeNumber(localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli())
    }
}