package top.threshold.ktscaffold.handler

import RainbowException
import cn.dev33.satoken.exception.NotLoginException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import top.threshold.ktscaffold.entity.ResultKt
import top.threshold.ktscaffold.entity.Slf4j.Companion.log
import top.threshold.ktscaffold.enums.RCode
import java.util.*


@RestControllerAdvice
class GlobalExceptionHandler(
) {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun notValidException(e: MethodArgumentNotValidException): ResultKt<*> {
        val msg = Objects.requireNonNull(e.bindingResult.fieldError)?.defaultMessage
        log.error("参数校验未通过", e)
        return ResultKt.fail<Any>(RCode.PARAMS_MISSING.code, msg)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseBody
    fun msgNotReadable(req: HttpServletRequest, e: HttpMessageNotReadableException): ResultKt<*> {
        log.warn("msgNotReadable , {}, {}", req.requestURI, e.message)
        return ResultKt.fail<Any>(RCode.PARAMS_INVALID.code, RCode.PARAMS_INVALID.msg)
    }

    @ExceptionHandler(NotLoginException::class)
    @ResponseBody
    fun notLoginExceptionHandler(req: HttpServletRequest, e: NotLoginException): ResultKt<String?> {
        log.error("notLoginException , {}, {}", req.requestURI, e.loginType)
        var msg = RCode.LOGIN_EXPIRED.msg
        if (e.type == NotLoginException.TOKEN_TIMEOUT) {
            msg = RCode.LOGIN_EXPIRED.msg
        } else if (e.type == NotLoginException.BE_REPLACED) {
            msg = NotLoginException.BE_REPLACED_MESSAGE
        } else if (e.type == NotLoginException.KICK_OUT) {
            msg = NotLoginException.KICK_OUT_MESSAGE
        }
        return ResultKt.fail(RCode.UNAUTHORIZED.code, msg)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseBody
    fun badMethodHandler(req: HttpServletRequest, e: HttpRequestMethodNotSupportedException): ResultKt<String?> {
        log.warn("HttpRequestMethodNotSupportedException , {}, {}", req.requestURI, e.message)
        return ResultKt.fail(
            RCode.METHOD_NOT_SUPPORT.code, RCode.METHOD_NOT_SUPPORT.msg
        )
    }

    @ExceptionHandler(RainbowException::class)
    @ResponseBody
    fun RainbowExceptionHandler(req: HttpServletRequest, e: RainbowException): ResultKt<*> {
        printStackTrace(e)
        return ResultKt.fail<Any>(e.code, e.msg)
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun ExceptionHandler(e: Exception?): ResultKt<String?> {
        log.error("Exception:", e)
        return ResultKt.fail(RCode.HTTP_FAILURE.code, RCode.HTTP_FAILURE.msg)
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