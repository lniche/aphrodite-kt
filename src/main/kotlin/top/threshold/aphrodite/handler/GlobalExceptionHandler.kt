package top.threshold.aphrodite.handler

import KtException
import cn.dev33.satoken.exception.NotLoginException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import top.threshold.aphrodite.entity.ResultKt
import top.threshold.aphrodite.entity.Slf4j
import top.threshold.aphrodite.entity.Slf4j.Companion.log
import top.threshold.aphrodite.enums.KtCode

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler(
) {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun notValidException(e: MethodArgumentNotValidException): ResultKt<*> {
        val message = e.bindingResult.fieldError?.defaultMessage!!
        log.error("参数校验未通过", e)
        return ResultKt.fail<Any>(KtCode.PARAMS_MISSING.code, message)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseBody
    fun messageNotReadable(req: HttpServletRequest, e: HttpMessageNotReadableException): ResultKt<*> {
        log.warn("messageNotReadable , {}, {}", req.requestURI, e.message)
        return ResultKt.fail<Any>(KtCode.PARAMS_INVALID.code, KtCode.PARAMS_INVALID.message)
    }

    @ExceptionHandler(NotLoginException::class)
    @ResponseBody
    fun notLoginExceptionHandler(req: HttpServletRequest, e: NotLoginException): ResultKt<String?> {
        log.error("notLoginException , {}, {}", req.requestURI, e.loginType)
        var message = KtCode.LOGIN_EXPIRED.message
        if (e.type == NotLoginException.TOKEN_TIMEOUT) {
            message = KtCode.LOGIN_EXPIRED.message
        } else if (e.type == NotLoginException.BE_REPLACED) {
            message = NotLoginException.BE_REPLACED_MESSAGE
        } else if (e.type == NotLoginException.KICK_OUT) {
            message = NotLoginException.KICK_OUT_MESSAGE
        }
        return ResultKt.fail(KtCode.UNAUTHORIZED.code, message)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseBody
    fun badMethodHandler(req: HttpServletRequest, e: HttpRequestMethodNotSupportedException): ResultKt<String?> {
        log.warn("HttpRequestMethodNotSupportedException , {}, {}", req.requestURI, e.message)
        return ResultKt.fail(
            KtCode.METHOD_NOT_SUPPORT.code, KtCode.METHOD_NOT_SUPPORT.message
        )
    }

    @ExceptionHandler(KtException::class)
    @ResponseBody
    fun RainbowExceptionHandler(req: HttpServletRequest, e: KtException): ResultKt<*> {
        printStackTrace(e)
        return ResultKt.fail<Any>(e.code, e.message!!)
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun ExceptionHandler(e: Exception?): ResultKt<String?> {
        log.error("Exception:", e)
        return ResultKt.fail(KtCode.HTTP_FAILURE.code, KtCode.HTTP_FAILURE.message)
    }

    /**
     * 打印异常堆栈
     *
     * @param t 异常
     */
    private fun printStackTrace(t: Throwable) {
        val stack = t.stackTrace
        val sb = StringBuilder()
        sb.append("\n")
        sb.append(t.javaClass.name).append(":").append(t.message).append("\n")
        sb.append("stack trace:\n")
        if (stack != null && stack.size > 0) {
            for (i in 2 until stack.size) {
                val trace = stack[i]
                sb.append("  at ").append(trace.className).append(".").append(trace.methodName).append("(")
                    .append(trace.fileName).append(":").append(trace.lineNumber).append(")\n")
            }
        }
        log.error(sb.toString())
    }
}