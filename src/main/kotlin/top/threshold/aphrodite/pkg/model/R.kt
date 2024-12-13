package top.threshold.aphrodite.pkg.model

import top.threshold.aphrodite.pkg.enums.Errors
import java.io.Serializable

open class R<T> : Serializable {
    var code: Int = 0

    var message: String = "ok"

    var data: T? = null

    companion object {
        fun <T> ok(): R<T> {
            return R()
        }

        fun <T> ok(data: T): R<T> {
            return ok(data, "ok")
        }

        fun <T> ok(data: T, message: String): R<T> {
            val resultkt = R<T>()
            resultkt.data = data
            resultkt.message = message
            return resultkt
        }

        fun <T> err(): R<T> {
            return err(Errors.ERR)
        }

        fun <T> err(message: String): R<T> {
            return err(Errors.ERR.code, message)
        }

        fun <T> err(code: Int, message: String): R<T> {
            val resultkt = R<T>()
            resultkt.code = code
            resultkt.message = message
            return resultkt
        }

        fun <T> err(errors: Errors): R<T> {
            val resultkt = R<T>()
            resultkt.code = errors.code
            resultkt.message = errors.message
            return resultkt
        }

        fun <T> err(errors: Errors, data: T): R<T> {
            val resultkt = R<T>()
            resultkt.code = errors.code
            resultkt.message = errors.message
            resultkt.data = data
            return resultkt
        }
    }
}
