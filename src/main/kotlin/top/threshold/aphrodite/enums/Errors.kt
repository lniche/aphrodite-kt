package top.threshold.aphrodite.enums

import top.threshold.aphrodite.exception.KtAssert

enum class Errors(override val code: Int, override val message: String) : KtAssert {
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

}