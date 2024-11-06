package top.threshold.aphrodite.app.aspect

import cn.hutool.core.util.StrUtil
import cn.hutool.json.JSONUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.multipart.MultipartFile
import top.threshold.aphrodite.app.entity.Slf4j.Companion.log

@Order(0)
@Aspect
@Component
class LogAspect {

    private val LINE_SEPARATOR = System.lineSeparator()

    @Pointcut("execution(public * top.threshold.aphrodite.controller..*.*(..))")
    fun requestAspect() {
    }

    @Around("requestAspect()")
    fun process(proceedingJoinPoint: ProceedingJoinPoint): Any {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        val request = requestAttributes!!.request
        if (request.requestURL.toString().endsWith("health.do")) {
            return proceedingJoinPoint.proceed()
        }
        log.info("========================================== Start ==========================================")
        log.info("URL            : {}", request.requestURL.toString())
        log.info("HTTP Method    : {}", request.method)
        log.info(
            "Class Method   : {}.{}",
            proceedingJoinPoint.signature.declaringTypeName,
            proceedingJoinPoint.signature.name
        )
        log.info("IP             : {}", request.remoteAddr)
        log.info("Request Args   : {}", getParams(proceedingJoinPoint))

        val startTime = System.currentTimeMillis()
        val proceed = proceedingJoinPoint.proceed()

        log.info("Response Args   : {}", StrUtil.sub(JSONUtil.toJsonStr(proceed), 0, 1024))
        log.info("Time-Consuming  : {} ms", System.currentTimeMillis() - startTime)
        log.info("=========================================== End ===========================================$LINE_SEPARATOR")
        return proceed
    }

    private fun getParams(joinPoint: JoinPoint): String? {
        var params: String? = ""
        if (joinPoint.args != null && joinPoint.args.isNotEmpty()) {
            for (i in joinPoint.args.indices) {
                val arg = joinPoint.args[i]
                if (arg is HttpServletResponse || arg is HttpServletRequest
                    || arg is MultipartFile || (arg is Array<*> && arg.isArrayOf<MultipartFile>())
                ) {
                    continue
                }
                try {
                    params += JSONUtil.toJsonStr(joinPoint.args[i])
                } catch (e1: Exception) {
                    log.error(e1.message)
                }
            }
        }
        return params
    }
}