package top.threshold.aphrodite.app.handler

import KtException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import top.threshold.aphrodite.pkg.entity.KtResult
import top.threshold.aphrodite.pkg.entity.Slf4j
import top.threshold.aphrodite.pkg.entity.Slf4j.Companion.log
import top.threshold.aphrodite.pkg.enums.Errors

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler(
) {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun notValidException(e: MethodArgumentNotValidException): KtResult<*> {
        val message = e.bindingResult.fieldError?.defaultMessage!!
        log.error("Parameter verification failed", e)
        return KtResult.err<Any>(Errors.ERR_BAD_REQUEST.code, message)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseBody
    fun messageNotReadable(req: HttpServletRequest, e: HttpMessageNotReadableException): KtResult<*> {
        log.warn("messageNotReadable , {}, {}", req.requestURI, e.message)
        return KtResult.err<Any>(Errors.ERR_BAD_REQUEST)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseBody
    fun badMethodHandler(req: HttpServletRequest, e: HttpRequestMethodNotSupportedException): KtResult<String?> {
        log.warn("HttpRequestMethodNotSupportedException , {}, {}", req.requestURI, e.message)
        return KtResult.err(Errors.ERR_METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(KtException::class)
    @ResponseBody
    fun KtExceptionHandler(req: HttpServletRequest, e: KtException): KtResult<*> {
        printStackTrace(e)
        return KtResult.err<Any>(e.code, e.message!!)
    }

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun ExceptionHandler(e: Exception?): KtResult<String?> {
        log.error("Exception:", e)
        return KtResult.err(Errors.ERR_INTERNAL_SERVER_ERROR.code, Errors.ERR_INTERNAL_SERVER_ERROR.message)
    }

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