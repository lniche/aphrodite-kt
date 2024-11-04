package top.threshold.aphrodite.entity

import top.threshold.aphrodite.enums.KtCode
import java.io.Serializable

open class ResultKt<T> : Serializable {
    /**
     * 错误码
     */
    var code: Int = KtCode.OK.code

    /**
     * 错误消息
     */
    var message: String = "ok"

    /**
     * 返回的实体类
     */
    var data: T? = null
    fun succeeded(): Boolean {
        return this.code == KtCode.OK.code
    }

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

        fun <T> fail(message: String): ResultKt<T> {
            return fail(KtCode.ERR.code, message)
        }

        fun <T> fail(code: Int, message: String): ResultKt<T> {
            val result = ResultKt<T>()
            result.code = code
            result.message = message
            return result
        }

        fun <T> fail(ktCode: KtCode): ResultKt<T> {
            val result = ResultKt<T>()
            result.code = ktCode.code
            result.message = ktCode.message
            return result
        }

        fun <T> fail(ktCode: KtCode, data: T): ResultKt<T> {
            val result = ResultKt<T>()
            result.code = ktCode.code
            result.message = ktCode.message
            result.data = data
            return result
        }
    }
}