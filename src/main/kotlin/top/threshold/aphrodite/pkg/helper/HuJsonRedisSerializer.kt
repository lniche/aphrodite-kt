package top.threshold.aphrodite.pkg.helper

import cn.hutool.json.JSONUtil
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.SerializationException
import java.nio.charset.StandardCharsets

class HuJsonRedisSerializer : RedisSerializer<Any?> {
    @Throws(SerializationException::class)
    override fun serialize(t: Any?): ByteArray? {
        return if (t == null) {
            ByteArray(0)
        } else {
            try {
                JSONUtil.toJsonStr(t).toByteArray(StandardCharsets.UTF_8)
            } catch (var3: Exception) {
                throw SerializationException("Could not serialize: " + var3.message, var3)
            }
        }
    }

    @Throws(SerializationException::class)
    override fun deserialize(bytes: ByteArray?): Any? {
        return if (bytes != null && bytes.size != 0) {
            try {
                String(bytes, StandardCharsets.UTF_8)
            } catch (var3: Exception) {
                throw SerializationException("Could not deserialize: " + var3.message, var3)
            }
        } else {
            null
        }
    }
}