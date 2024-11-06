package top.threshold.aphrodite.pkg.exception

import KtException


interface Assert {

    fun newException(vararg args: Any?): KtException?

    fun newException(t: Throwable?, vararg args: Any?): KtException?

    fun assertNotNull(obj: Any?) {
        if (null == obj) {
            throw newException()!!
        }
    }

    fun assertNotNull(obj: Any?, vararg args: Any?) {
        if (null == obj) {
            throw newException(*args)!!
        }
    }

    fun assertIsNull(obj: Any?) {
        if (null != obj) {
            throw newException()!!
        }
    }

    fun assertIsNull(obj: Any?, vararg args: Any?) {
        if (null != obj) {
            throw newException(*args)!!
        }
    }

    fun assertNotEmpty(str: String?) {
        if (null == str || "" == str.trim { it <= ' ' }) {
            throw newException()!!
        }
    }

    fun assertNotEmpty(str: String?, vararg args: Any?) {
        if (null == str || "" == str.trim { it <= ' ' }) {
            throw newException(*args)!!
        }
    }

    fun assertNotEmpty(arrays: Array<Any?>?) {
        if (null == arrays || arrays.size == 0) {
            throw newException()!!
        }
    }

    fun assertNotEmpty(arrays: Array<Any?>?, vararg args: Any?) {
        if (null == arrays || arrays.size == 0) {
            throw newException(*args)!!
        }
    }

    fun assertNotEmpty(c: Collection<*>?) {
        if (null == c || c.isEmpty()) {
            throw newException()!!
        }
    }

    fun assertNotEmpty(c: Collection<*>?, vararg args: Any?) {
        if (null == c || c.isEmpty()) {
            throw newException(*args)!!
        }
    }

    fun assertNotEmpty(map: Map<*, *>?) {
        if (null == map || map.isEmpty()) {
            throw newException()!!
        }
    }

    fun assertNotEmpty(map: Map<*, *>?, vararg args: Any?) {
        if (null == map || map.isEmpty()) {
            throw newException(*args)!!
        }
    }

    fun assertIsFalse(expression: Boolean) {
        if (expression) {
            throw newException()!!
        }
    }

    fun assertIsFalse(expression: Boolean, vararg args: Any?) {
        if (expression) {
            throw newException(*args)!!
        }
    }

    fun assertIsTrue(expression: Boolean) {
        if (!expression) {
            throw newException()!!
        }
    }

    fun assertIsTrue(expression: Boolean, vararg args: Any?) {
        if (!expression) {
            throw newException(*args)!!
        }
    }

    fun assertFail() {
        throw newException()!!
    }

    fun assertFail(vararg args: Any?) {
        throw newException(*args)!!
    }

    fun assertFail(t: Throwable?) {
        throw newException(t)!!
    }

    fun assertFail(t: Throwable?, vararg args: Any?) {
        throw newException(t, *args)!!
    }

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