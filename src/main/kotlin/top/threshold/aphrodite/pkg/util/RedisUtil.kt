package top.threshold.aphrodite.pkg.util

import cn.hutool.core.util.StrUtil
import cn.hutool.json.JSONUtil
import jakarta.annotation.Resource
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import top.threshold.aphrodite.pkg.entity.Slf4j.Companion.log
import java.util.concurrent.TimeUnit

@Component
class RedisUtil {

    @Resource
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    @Resource
    private lateinit var stringRedisTemplate: StringRedisTemplate

    fun expire(key: String, time: Long): Boolean {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS)
            }
            return true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    fun getExpire(key: String): Long {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS)
    }

    fun hasKey(key: String): Boolean {
        try {
            return stringRedisTemplate.hasKey(key)
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    fun del(vararg key: String) {
        if (key.isNotEmpty()) {
            if (key.size == 1) {
                stringRedisTemplate.delete(key[0])
            } else {
                stringRedisTemplate.delete(key.toList())
            }
        }
    }

    operator fun get(key: String?): Any? {
        return redisTemplate.opsForValue().get(key!!)
    }

    fun getStr(key: String): String? {
        return stringRedisTemplate.opsForValue().get(key)
    }

    fun getInt(key: String?): Int {
        val str = stringRedisTemplate.opsForValue().get(key!!)
        return if (StrUtil.isBlank(str)) 0 else str!!.toInt()

    }

    fun getBool(key: String?): Boolean {
        val str = stringRedisTemplate.opsForValue().get(key!!)
        return if (StrUtil.isBlank(str)) false else str!!.toBoolean()

    }

    fun getLong(key: String?): Long {
        val str = stringRedisTemplate.opsForValue().get(key!!)
        return if (StrUtil.isBlank(str)) 0 else str!!.toLong()

    }

    fun <T> getObj(key: String?, clazz: Class<T>): T? {
        return try {
            val json = redisTemplate.opsForValue()[key!!] as String?
            if (StrUtil.isBlankIfStr(json)) {
                null
            } else JSONUtil.toBean(json, clazz)
        } catch (e: Exception) {
            log.error("redis error", e)
            null
        }
    }

    fun setLong(key: String, str: Long): Boolean {
        return try {
            stringRedisTemplate.opsForValue().set(key, str.toString())
            true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }
    }

    fun setStr(key: String, str: String): Boolean {
        return try {
            stringRedisTemplate.opsForValue().set(key, str)
            true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }
    }

    fun setBool(key: String, str: Boolean): Boolean {
        return try {
            stringRedisTemplate.opsForValue().set(key, str.toString())
            true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }
    }

    fun setInt(key: String, str: Int): Boolean {
        return try {
            stringRedisTemplate.opsForValue().set(key, str.toString())
            true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }
    }

    fun setInt(key: String, str: Int, time: Long): Boolean {
        return try {
            if (time > 0) {
                stringRedisTemplate.opsForValue().set(key, str.toString(), time, TimeUnit.SECONDS)
            } else {
                setStr(key, str.toString())
            }
            true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }
    }

    fun setStr(key: String, str: String, time: Long): Boolean {
        return try {
            if (time > 0) {
                stringRedisTemplate.opsForValue().set(key, str, time, TimeUnit.SECONDS)
            } else {
                setStr(key, str)
            }
            true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }
    }

    operator fun set(key: String, value: Any): Boolean {
        try {
            redisTemplate.opsForValue().set(key, value)
            return true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    operator fun set(key: String, value: Any, time: Long): Boolean {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS)
            } else {
                set(key, value)
            }
            return true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    fun incr(key: String, delta: Long): Long {
        if (delta < 0) {
            throw RuntimeException("递增因子必须大于0")
        }
        return redisTemplate.opsForValue().increment(key, delta)!!
    }

    fun decr(key: String, delta: Long): Long {
        if (delta < 0) {
            throw RuntimeException("递减因子必须大于0")
        }
        return redisTemplate.opsForValue().increment(key, -delta)!!
    }

    fun decrDelIfZero(key: String, delta: Long): Long {
        if (delta < 0) {
            throw RuntimeException("递减因子必须大于0")
        }
        val l = redisTemplate.opsForValue().increment(key, -delta)!!
        if (getLong(key) <= 0) del(key)
        return l
    }

    fun hget(key: String, item: String): Any? {
        return redisTemplate.opsForHash<Any, Any>().get(key, item)
    }

    fun hmget(key: String): Map<Any, Any> {
        return redisTemplate.opsForHash<Any, Any>().entries(key)
    }

    fun hmset(key: String, map: Map<String, Any>): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().putAll(key, map)
            return true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    fun hmset(key: String, map: Map<String, Any>, time: Long): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().putAll(key, map)
            if (time > 0) {
                expire(key, time)
            }
            return true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    fun hset(key: String, item: String, value: Any): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().put(key, item, value)
            return true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    fun hset(key: String, item: String, value: Any, time: Long): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().put(key, item, value)
            if (time > 0) {
                expire(key, time)
            }
            return true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    fun hdel(key: String, vararg item: Any) {
        redisTemplate.opsForHash<Any, Any>().delete(key, *item)
    }

    fun hHasKey(key: String, item: String): Boolean {
        return redisTemplate.opsForHash<Any, Any>().hasKey(key, item)
    }

    fun hincr(key: String, item: String, by: Double): Double {
        return redisTemplate.opsForHash<Any, Any>().increment(key, item, by)
    }

    fun hdecr(key: String, item: String, by: Double): Double {
        return redisTemplate.opsForHash<Any, Any>().increment(key, item, -by)
    }

    fun sGet(key: String): Set<Any>? {
        try {
            return redisTemplate.opsForSet().members(key)
        } catch (e: Exception) {
            log.error("redis error", e)
            return null
        }

    }

    fun sHasKey(key: String, value: Any): Boolean {
        try {
            return redisTemplate.opsForSet().isMember(key, value)!!
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    fun sSet(key: String, vararg values: Any): Long {
        try {
            return redisTemplate.opsForSet().add(key, *values)!!
        } catch (e: Exception) {
            log.error("redis error", e)
            return 0
        }
    }

    fun sSetAndTime(key: String, time: Long, vararg values: Any): Long {
        try {
            val count = redisTemplate.opsForSet().add(key, *values)
            if (time > 0)
                expire(key, time)
            return count!!
        } catch (e: Exception) {
            log.error("redis error", e)
            return 0
        }

    }

    fun sGetSetSize(key: String): Long {
        try {
            return redisTemplate.opsForSet().size(key)!!
        } catch (e: Exception) {
            log.error("redis error", e)
            return 0
        }

    }

    fun setRemove(key: String, vararg values: Any): Long {
        try {
            val count = redisTemplate.opsForSet().remove(key, *values)
            return count!!
        } catch (e: Exception) {
            log.error("redis error", e)
            return 0
        }

    }

    fun sAll(key: String): Set<Any>? {
        return redisTemplate.opsForSet().members(key)
    }

    fun lGet(key: String, start: Long, end: Long): List<Any>? {
        try {
            return redisTemplate.opsForList().range(key, start, end)
        } catch (e: Exception) {
            log.error("redis error", e)
            return null
        }

    }

    fun lGetListSize(key: String): Long {
        try {
            return redisTemplate.opsForList().size(key)!!
        } catch (e: Exception) {
            log.error("redis error", e)
            return 0
        }

    }

    fun lGetIndex(key: String, index: Long): Any? {
        try {
            return redisTemplate.opsForList().index(key, index)
        } catch (e: Exception) {
            log.error("redis error", e)
            return null
        }

    }

    fun llSet(key: String, value: Any): Boolean {
        return try {
            redisTemplate.opsForList().leftPush(key, value)
            true
        } catch (e: Exception) {
            log.error("redis error", e)
            false
        }

    }

    fun lrSet(key: String, value: Any): Boolean {
        try {
            redisTemplate.opsForList().rightPush(key, value)
            return true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    fun lSet(key: String, value: Any, time: Long): Boolean {
        try {
            redisTemplate.opsForList().rightPush(key, value)
            if (time > 0)
                expire(key, time)
            return true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    fun lSet(key: String, value: List<Any>): Boolean {
        try {
            redisTemplate.opsForList().rightPushAll(key, *value.toTypedArray())
            return true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    fun lSet(key: String, value: List<Any>, time: Long): Boolean {
        try {
            redisTemplate.opsForList().rightPushAll(key, *value.toTypedArray())
            if (time > 0)
                expire(key, time)
            return true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    fun lUpdateIndex(key: String, index: Long, value: Any): Boolean {
        try {
            redisTemplate.opsForList().set(key, index, value)
            return true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    fun lRemove(key: String, count: Long, value: Any): Long {
        try {
            val remove = redisTemplate.opsForList().remove(key, count, value)
            return remove!!
        } catch (e: Exception) {
            log.error("redis error", e)
            return 0
        }

    }

    fun ltrim(key: String, start: Long, end: Long): Boolean {
        try {
            redisTemplate.opsForList().trim(key, start, end)
            return true
        } catch (e: Exception) {
            log.error("redis error", e)
            return false
        }

    }

    fun nextId(key: String): Long? {
        return redisTemplate.opsForValue().increment(key)
    }

    fun setKey(s: String) {
        stringRedisTemplate.opsForValue().set(s, "")
    }

    fun setKey(s: String, time: Long) {
        stringRedisTemplate.opsForValue().set(s, "", time, TimeUnit.SECONDS)
    }
}
