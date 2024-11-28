package top.threshold.aphrodite.pkg.entity

import top.threshold.aphrodite.pkg.enums.Errors
import java.io.Serializable

open class KtResult<T> : Serializable {
    var code: Int = 0

    var message: String = "ok"

    var data: T? = null

    companion object {
        fun <T> ok(): KtResult<T> {
            return KtResult()
        }

        fun <T> ok(data: T): KtResult<T> {
            return ok(data, "ok")
        }

        fun <T> ok(data: T, message: String): KtResult<T> {
            val resultkt = KtResult<T>()
            resultkt.data = data
            resultkt.message = message
            return resultkt
        }

        fun <T> err(): KtResult<T> {
            return err(Errors.ERR)
        }

        fun <T> err(message: String): KtResult<T> {
            return err(Errors.ERR.code, message)
        }

        fun <T> err(code: Int, message: String): KtResult<T> {
            val resultkt = KtResult<T>()
            resultkt.code = code
            resultkt.message = message
            return resultkt
        }

        fun <T> err(errors: Errors): KtResult<T> {
            val resultkt = KtResult<T>()
            resultkt.code = errors.code
            resultkt.message = errors.message
            return resultkt
        }

        fun <T> err(errors: Errors, data: T): KtResult<T> {
            val resultkt = KtResult<T>()
            resultkt.code = errors.code
            resultkt.message = errors.message
            resultkt.data = data
            return resultkt
        }
    }
}
