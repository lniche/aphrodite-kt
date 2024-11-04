package top.threshold.aphrodite.enums

import top.threshold.aphrodite.exception.KtAssert

enum class KtCode(override val code: Int, override val message: String) : KtAssert {
    // common errors
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CONFLICT(409, "Conflict"),
    HTTP_FAILURE(500, "Internal Server Error"),

    OK(0, "ok"),
    ERR(-1, "err"),

    // more biz errors
    ERR_EMAIL_ALREADY_USE(1001, "The email is already in use."),
    ERR_PHONE_ALREADY_USE(1002, "The email is already in use."),
    ;

}