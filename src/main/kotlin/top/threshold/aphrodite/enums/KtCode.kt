package top.threshold.aphrodite.enums

import top.threshold.aphrodite.exception.KtAssert

enum class KtCode(override val code: Int, override val message: String) : KtAssert {
    // common errors
    ERR_BAD_REQUEST(400, "Bad Request"),
    ERR_UNAUTHORIZED(401, "Unauthorized"),
    ERR_FORBIDDEN(403, "Forbidden"),
    ERR_NOT_FOUND(404, "Not Found"),
    ERR_METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    ERR_CONFLICT(409, "Conflict"),
    ERR_INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    OK(0, "ok"),
    ERR(-1, "err"),

    // more biz errors
    ERR_EMAIL_ALREADY_USE(1001, "The email is already in use."),
    ERR_PHONE_ALREADY_USE(1002, "The email is already in use."),
    ;

}