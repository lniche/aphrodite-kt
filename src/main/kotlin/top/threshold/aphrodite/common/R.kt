package top.threshold.aphrodite.common

import kotlinx.serialization.Serializable

@Serializable
data class R<T>(
    val message: String,
    val data: T? = null,
    val code: Int
) {
    companion object {
        fun <T> ok(data: T? = null): R<T> {
            return R(
                message = "ok",
                data = data,
                code = 0
            )
        }

        fun <T> err(message: String = "err", code: Int = -1): R<T> {
            return R(
                message = message,
                code = code
            )
        }

        fun <T> err(error: ErrorCode, message: String = "err", code: Int = -1): R<T> {
            return R(
                message = message,
                code = error.code
            )
        }

        fun <T> err(error: ErrorCode, data: T? = null): R<T> {
            return R(
                message = error.message,
                data = data,
                code = error.code
            )
        }

    }
}


enum class ErrorCode(val code: Int, val message: String) {
    // common errors
    ERR(-1, "err"),
    ERR_BAD_REQUEST(400, "Bad Request"),
    ERR_UNAUTHORIZED(401, "Unauthorized"),
    ERR_FORBIDDEN(403, "Forbidden"),
    ERR_NOT_FOUND(404, "Not Found"),
    ERR_METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    ERR_INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // more biz errors
    ERR_DATA(1001, "Data Error"),
    ERR_SERVICE(1002, "Service Error"),
    ;

    companion object {
        fun getByCode(code: Int): ErrorCode {
            return ErrorCode.entries.find { it.code == code } ?: ERR_INTERNAL_SERVER_ERROR
        }
    }
}
