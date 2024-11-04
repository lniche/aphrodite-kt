package top.threshold.aphrodite.entity

import top.threshold.aphrodite.enums.Errors
import java.io.Serializable

open class Result<T> : Serializable {
    var code: Int = 0

    var message: String = "ok"

    var data: T? = null

    companion object {
        fun <T> ok(): Result<T> {
            return Result()
        }

        fun <T> ok(data: T): Result<T> {
            return ok(data, "ok")
        }

        fun <T> ok(data: T, message: String): Result<T> {
            val resultkt = Result<T>()
            resultkt.data = data
            resultkt.message = message
            return resultkt
        }

        fun <T> err(): Result<T> {
            return err(Errors.ERR)
        }

        fun <T> err(message: String): Result<T> {
            return err(Errors.ERR.code, message)
        }

        fun <T> err(code: Int, message: String): Result<T> {
            val resultkt = Result<T>()
            resultkt.code = code
            resultkt.message = message
            return resultkt
        }

        fun <T> err(errors: Errors): Result<T> {
            val resultkt = Result<T>()
            resultkt.code = errors.code
            resultkt.message = errors.message
            return resultkt
        }

        fun <T> err(errors: Errors, data: T): Result<T> {
            val resultkt = Result<T>()
            resultkt.code = errors.code
            resultkt.message = errors.message
            resultkt.data = data
            return resultkt
        }
    }
}