package top.threshold.aphrodite.exception

import KtException

/**
 *
 *
 * 枚举类断言，提供简便的方式判断条件，并在条件满足时抛出异常
 *
 *
 *
 * 返回码和返回信息定义在枚举类中，在本断言方法中，传递返回信息需要的参数
 *
 *
 * @author Edward Guo
 * @date 2019/10/21 1:50 下午
 */
interface Assert {
    /**
     * 创建异常
     *
     * @param args 参数
     * @return 异常
     */
    fun newException(vararg args: Any?): KtException?

    /**
     * 创建异常
     *
     * @param t    抛出
     * @param args 参数
     * @return 异常
     */
    fun newException(t: Throwable?, vararg args: Any?): KtException?

    /**
     *
     *
     * 断言对象`obj`非空，
     * 如果对象`obj`为空，则抛出异常
     *
     *
     * @param obj 待判断对象
     */
    fun assertNotNull(obj: Any?) {
        if (null == obj) {
            throw newException()!!
        }
    }

    /**
     *
     *
     * 断言对象`obj`非空，
     * 如果对象`obj`为空，则抛出异常
     *
     *
     *
     * 异常信息`message`支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     *
     * @param obj  待判断对象
     * @param args message占位符对应的参数列表
     */
    fun assertNotNull(obj: Any?, vararg args: Any?) {
        if (null == obj) {
            throw newException(*args)!!
        }
    }

    /**
     *
     *
     * 断言对象`obj`为空，
     * 如果对象`obj`非空，则抛出异常
     *
     *
     * @param obj 待判断对象
     */
    fun assertIsNull(obj: Any?) {
        if (null != obj) {
            throw newException()!!
        }
    }

    /**
     *
     *
     * 断言对象`obj`为空，
     * 如果对象`obj`非空，则抛出异常
     *
     *
     *
     * 异常信息`message`支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     *
     * @param obj  待判断对象
     * @param args message占位符对应的参数列表
     */
    fun assertIsNull(obj: Any?, vararg args: Any?) {
        if (null != obj) {
            throw newException(*args)!!
        }
    }

    /**
     *
     *
     * 断言字符串`str`不为空串（长度为0），
     * 如果字符串`str`为空串，则抛出异常
     *
     *
     * @param str 待判断字符串
     */
    fun assertNotEmpty(str: String?) {
        if (null == str || "" == str.trim { it <= ' ' }) {
            throw newException()!!
        }
    }

    /**
     *
     *
     * 断言字符串`str`不为空串（长度为0），
     * 如果字符串`str`为空串，则抛出异常
     *
     *
     *
     * 异常信息`message`支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     *
     * @param str  待判断字符串
     * @param args message占位符对应的参数列表
     */
    fun assertNotEmpty(str: String?, vararg args: Any?) {
        if (null == str || "" == str.trim { it <= ' ' }) {
            throw newException(*args)!!
        }
    }

    /**
     *
     *
     * 断言数组`arrays`大小不为0，
     * 如果数组`arrays`大小不为0，则抛出异常
     *
     *
     * @param arrays 待判断数组
     */
    fun assertNotEmpty(arrays: Array<Any?>?) {
        if (null == arrays || arrays.size == 0) {
            throw newException()!!
        }
    }

    /**
     *
     *
     * 断言数组`arrays`大小不为0，
     * 如果数组`arrays`大小不为0，则抛出异常
     *
     *
     *
     * 异常信息`message`支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     *
     * @param arrays 待判断数组
     * @param args   message占位符对应的参数列表
     */
    fun assertNotEmpty(arrays: Array<Any?>?, vararg args: Any?) {
        if (null == arrays || arrays.size == 0) {
            throw newException(*args)!!
        }
    }

    /**
     *
     *
     * 断言集合`c`大小不为0，
     * 如果集合`c`大小不为0，则抛出异常
     *
     *
     * @param c 待判断集合
     */
    fun assertNotEmpty(c: Collection<*>?) {
        if (null == c || c.isEmpty()) {
            throw newException()!!
        }
    }

    /**
     *
     *
     * 断言集合`c`大小不为0，
     * 如果集合`c`大小不为0，则抛出异常
     *
     *
     *
     * 异常信息`message`支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     *
     * @param c    待判断集合
     * @param args message占位符对应的参数列表
     */
    fun assertNotEmpty(c: Collection<*>?, vararg args: Any?) {
        if (null == c || c.isEmpty()) {
            throw newException(*args)!!
        }
    }

    /**
     *
     *
     * 断言映射`map`大小不为0，
     * 如果映射`map`大小不为0，则抛出异常
     *
     *
     * @param map 待判断映射
     */
    fun assertNotEmpty(map: Map<*, *>?) {
        if (null == map || map.isEmpty()) {
            throw newException()!!
        }
    }

    /**
     *
     *
     * 断言映射`map`大小不为0，
     * 如果映射`map`大小不为0，则抛出异常
     *
     *
     *
     * 异常信息`message`支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     *
     * @param map  待判断映射
     * @param args message占位符对应的参数列表
     */
    fun assertNotEmpty(map: Map<*, *>?, vararg args: Any?) {
        if (null == map || map.isEmpty()) {
            throw newException(*args)!!
        }
    }

    /**
     *
     *
     * 断言布尔值`expression`为false，
     * 如果布尔值`expression`为true，则抛出异常
     *
     *
     * @param expression 待判断布尔变量
     */
    fun assertIsFalse(expression: Boolean) {
        if (expression) {
            throw newException()!!
        }
    }

    /**
     *
     *
     * 断言布尔值`expression`为false，
     * 如果布尔值`expression`为true，则抛出异常
     *
     *
     *
     * 异常信息`message`支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     *
     * @param expression 待判断布尔变量
     * @param args       message占位符对应的参数列表
     */
    fun assertIsFalse(expression: Boolean, vararg args: Any?) {
        if (expression) {
            throw newException(*args)!!
        }
    }

    /**
     *
     *
     * 断言布尔值`expression`为true，
     * 如果布尔值`expression`为false，则抛出异常
     *
     *
     * @param expression 待判断布尔变量
     */
    fun assertIsTrue(expression: Boolean) {
        if (!expression) {
            throw newException()!!
        }
    }

    /**
     *
     *
     * 断言布尔值`expression`为true，
     * 如果布尔值`expression`为false，则抛出异常
     *
     *
     *
     * 异常信息`message`支持传递参数方式，避免在判断之前进行字符串拼接操作
     *
     *
     * @param expression 待判断布尔变量
     * @param args       message占位符对应的参数列表
     */
    fun assertIsTrue(expression: Boolean, vararg args: Any?) {
        if (!expression) {
            throw newException(*args)!!
        }
    }

    /**
     *
     * 直接抛出异常
     */
    fun assertFail() {
        throw newException()!!
    }

    /**
     *
     * 直接抛出异常
     *
     * @param args message占位符对应的参数列表
     */
    fun assertFail(vararg args: Any?) {
        throw newException(*args)!!
    }

    /**
     *
     * 直接抛出异常，并包含原异常信息
     *
     *
     * 当捕获非运行时异常（非继承[RuntimeException]）时，并该异常进行业务描述时，
     * 必须传递原始异常，作为新异常的cause
     *
     *
     * @param t 原始异常
     */
    fun assertFail(t: Throwable?) {
        throw newException(t)!!
    }

    /**
     *
     * 直接抛出异常，并包含原异常信息
     *
     *
     * 当捕获非运行时异常（非继承[RuntimeException]）时，并该异常进行业务描述时，
     * 必须传递原始异常，作为新异常的cause
     *
     *
     * @param t    原始异常
     * @param args message占位符对应的参数列表
     */
    fun assertFail(t: Throwable?, vararg args: Any?) {
        throw newException(t, *args)!!
    }

    /**
     *
     *
     * 断言对象`o1`与对象`o2`相等，
     * 此处的相等指(o1.equals(o2)为true）
     * 如果对象不相等，则抛出异常
     *
     *
     * @param o1 待判断对象，若`o1`为null，也当作不相等处理
     * @param o2 待判断对象
     */
    fun assertEquals(o1: Any?, o2: Any) {
        if (o1 === o2) {
            return
        }
        if (o1 == null) {
            throw newException()!!
        }
        if (o1 != o2) {
            throw newException()!!
        }
    }

    /**
     *
     *
     * 断言对象`o1`与对象`o2`相等，
     * 此处的相等指(o1.equals(o2)为true）
     * 如果对象不相等，则抛出异常
     *
     *
     * @param o1   待判断对象，若`o1`为null，也当作不相等处理
     * @param o2   待判断对象
     * @param args message占位符对应的参数列表
     */
    fun assertEquals(o1: Any?, o2: Any, vararg args: Any?) {
        if (o1 === o2) {
            return
        }
        if (o1 == null) {
            throw newException(*args)!!
        }
        if (o1 != o2) {
            throw newException(*args)!!
        }
    }
}