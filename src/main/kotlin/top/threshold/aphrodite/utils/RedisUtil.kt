package top.threshold.aphrodite.utils

import cn.hutool.core.util.StrUtil
import cn.hutool.json.JSONUtil
import jakarta.annotation.Resource
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisUtil {

    @Resource
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    @Resource
    private lateinit var stringRedisTemplate: StringRedisTemplate

    // =============================common============================
    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    fun expire(key: String, time: Long): Boolean {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    fun getExpire(key: String): Long {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS)
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    fun hasKey(key: String): Boolean {
        try {
            return stringRedisTemplate.hasKey(key)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    fun del(vararg key: String) {
        if (key.isNotEmpty()) {
            if (key.size == 1) {
                stringRedisTemplate.delete(key[0])
            } else {
                stringRedisTemplate.delete(key.toList())
            }
        }
    }

    // ============================String=============================
    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
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
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    fun setLong(key: String, str: Long): Boolean {
        return try {
            stringRedisTemplate.opsForValue().set(key, str.toString())
            true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun setStr(key: String, str: String): Boolean {
        return try {
            stringRedisTemplate.opsForValue().set(key, str)
            true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun setBool(key: String, str: Boolean): Boolean {
        return try {
            stringRedisTemplate.opsForValue().set(key, str.toString())
            true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun setInt(key: String, str: Int): Boolean {
        return try {
            stringRedisTemplate.opsForValue().set(key, str.toString())
            true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
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
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
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
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return false
        }
    }


    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    operator fun set(key: String, value: Any): Boolean {
        try {
            redisTemplate.opsForValue().set(key, value)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    operator fun set(key: String, value: Any, time: Long): Boolean {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS)
            } else {
                set(key, value)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 递增
     * @param key 键
     * @param delta 要增加几(大于0)
     * @return
     */
    fun incr(key: String, delta: Long): Long {
        if (delta < 0) {
            throw RuntimeException("递增因子必须大于0")
        }
        return redisTemplate.opsForValue().increment(key, delta)!!
    }

    /**
     * 递减
     * @param key 键
     * @param delta 要减少几(小于0)
     * @return
     */
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

    // ================================Map=================================
    /**
     * HashGet
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    fun hget(key: String, item: String): Any? {
        return redisTemplate.opsForHash<Any, Any>().get(key, item)
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    fun hmget(key: String): Map<Any, Any> {
        return redisTemplate.opsForHash<Any, Any>().entries(key)
    }

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    fun hmset(key: String, map: Map<String, Any>): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().putAll(key, map)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * HashSet 并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    fun hmset(key: String, map: Map<String, Any>, time: Long): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().putAll(key, map)
            if (time > 0) {
                expire(key, time)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    fun hset(key: String, item: String, value: Any): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().put(key, item, value)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    fun hset(key: String, item: String, value: Any, time: Long): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().put(key, item, value)
            if (time > 0) {
                expire(key, time)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    fun hdel(key: String, vararg item: Any) {
        redisTemplate.opsForHash<Any, Any>().delete(key, *item)
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    fun hHasKey(key: String, item: String): Boolean {
        return redisTemplate.opsForHash<Any, Any>().hasKey(key, item)
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param item 项
     * @param by 要增加几(大于0)
     * @return
     */
    fun hincr(key: String, item: String, by: Double): Double {
        return redisTemplate.opsForHash<Any, Any>().increment(key, item, by)
    }

    /**
     * hash递减
     * @param key 键
     * @param item 项
     * @param by 要减少记(小于0)
     * @return
     */
    fun hdecr(key: String, item: String, by: Double): Double {
        return redisTemplate.opsForHash<Any, Any>().increment(key, item, -by)
    }

    // ============================set=============================
    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return
     */
    fun sGet(key: String): Set<Any>? {
        try {
            return redisTemplate.opsForSet().members(key)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 根据value从一个set中查询,是否存在
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     */
    fun sHasKey(key: String, value: Any): Boolean {
        try {
            return redisTemplate.opsForSet().isMember(key, value)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 将数据放入set缓存
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    fun sSet(key: String, vararg values: Any): Long {
        try {
            return redisTemplate.opsForSet().add(key, *values)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }
    }

    /**
     * 将set数据放入缓存
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    fun sSetAndTime(key: String, time: Long, vararg values: Any): Long {
        try {
            val count = redisTemplate.opsForSet().add(key, *values)
            if (time > 0)
                expire(key, time)
            return count!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    /**
     * 获取set缓存的长度
     * @param key 键
     * @return
     */
    fun sGetSetSize(key: String): Long {
        try {
            return redisTemplate.opsForSet().size(key)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    /**
     * 移除值为value的
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    fun setRemove(key: String, vararg values: Any): Long {
        try {
            val count = redisTemplate.opsForSet().remove(key, *values)
            return count!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    /**
     * 获取全部
     *
     * @param key
     * @return
     */
    fun sAll(key: String): Set<Any>? {
        return redisTemplate.opsForSet().members(key)
    }

    // ===============================list=================================

    /**
     * 获取list缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束 0 到 -1代表所有值
     * @return
     */
    fun lGet(key: String, start: Long, end: Long): List<Any>? {
        try {
            return redisTemplate.opsForList().range(key, start, end)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 获取list缓存的长度
     * @param key 键
     * @return
     */
    fun lGetListSize(key: String): Long {
        try {
            return redisTemplate.opsForList().size(key)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    fun lGetIndex(key: String, index: Long): Any? {
        try {
            return redisTemplate.opsForList().index(key, index)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    fun llSet(key: String, value: Any): Boolean {
        return try {
            redisTemplate.opsForList().leftPush(key, value)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    fun lrSet(key: String, value: Any): Boolean {
        try {
            redisTemplate.opsForList().rightPush(key, value)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    fun lSet(key: String, value: Any, time: Long): Boolean {
        try {
            redisTemplate.opsForList().rightPush(key, value)
            if (time > 0)
                expire(key, time)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    fun lSet(key: String, value: List<Any>): Boolean {
        try {
            redisTemplate.opsForList().rightPushAll(key, *value.toTypedArray())
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 将list放入缓存
     *
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    fun lSet(key: String, value: List<Any>, time: Long): Boolean {
        try {
            redisTemplate.opsForList().rightPushAll(key, *value.toTypedArray())
            if (time > 0)
                expire(key, time)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    fun lUpdateIndex(key: String, index: Long, value: Any): Boolean {
        try {
            redisTemplate.opsForList().set(key, index, value)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    fun lRemove(key: String, count: Long, value: Any): Long {
        try {
            val remove = redisTemplate.opsForList().remove(key, count, value)
            return remove!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    fun ltrim(key: String, start: Long, end: Long): Boolean {
        try {
            redisTemplate.opsForList().trim(key, start, end)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
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