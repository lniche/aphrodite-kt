package top.threshold.aphrodite.entity

import top.threshold.aphrodite.enums.Errors
import java.io.Serializable

open class ResultKt<T> : Serializable {
    /**
     * 错误码
     */
    var code: Int = 0

    /**
     * 错误消息
     */
    var message: String = "ok"

    /**
     * 返回的实体类
     */
    var data: T? = null

    companion object {
        fun <T> success(): ResultKt<T> {
            return ResultKt()
        }

        fun <T> success(data: T): ResultKt<T> {
            return success(data, "ok")
        }

        fun <T> success(data: T, message: String): ResultKt<T> {
            val result = ResultKt<T>()
            result.data = data
            result.message = message
            return result
        }

        fun <T> fail(): ResultKt<T> {
            return fail(Errors.ERR)
        }

        fun <T> fail(message: String): ResultKt<T> {
            return fail(Errors.ERR.code, message)
        }

        fun <T> fail(code: Int, message: String): ResultKt<T> {
            val result = ResultKt<T>()
            result.code = code
            result.message = message
            return result
        }

        fun <T> fail(errors: Errors): ResultKt<T> {
            val result = ResultKt<T>()
            result.code = errors.code
            result.message = errors.message
            return result
        }

        fun <T> fail(errors: Errors, data: T): ResultKt<T> {
            val result = ResultKt<T>()
            result.code = errors.code
            result.message = errors.message
            result.data = data
            return result
        }
    }
}