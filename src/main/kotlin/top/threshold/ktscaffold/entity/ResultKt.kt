package top.threshold.ktscaffold.entity

import lombok.Data
import top.threshold.ktscaffold.enums.RCode
import java.io.Serializable

@Data
open class ResultKt<T> : Serializable {
    /**
     * 错误码
     */
    var code: String = RCode.OK.code

    /**
     * 错误消息
     */
    var msg: String? = null

    /**
     * 返回的实体类
     */
    var data: T? = null
    fun succeeded(): Boolean {
        return this.code == RCode.OK.code
    }

    companion object {
        fun <T> success(): ResultKt<T> {
            return ResultKt()
        }

        fun <T> success(data: T): ResultKt<T> {
            return success(data, null)
        }

        fun <T> success(data: T, msg: String?): ResultKt<T> {
            val result = ResultKt<T>()
            result.data = data
            result.msg = msg
            return result
        }

        fun <T> fail(msg: String?): ResultKt<T?> {
            return fail(RCode.UNKNOW.code, msg)
        }

        fun <T> fail(code: String?, msg: String?): ResultKt<T> {
            val result = ResultKt<T>()
            result.code = code!!
            result.msg = msg
            return result
        }

        fun <T> fail(error: RCode, data: T): ResultKt<T> {
            val result = ResultKt<T>()
            result.code = error.code
            result.msg = error.msg
            result.data = data
            return result
        }
    }
}