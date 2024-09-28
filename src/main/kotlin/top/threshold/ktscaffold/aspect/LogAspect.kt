package top.threshold.ktscaffold.aspect

import cn.hutool.core.util.StrUtil
import cn.hutool.json.JSONUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
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
import top.threshold.ktscaffold.entity.Slf4j.Companion.log

@Slf4j
@Order(0)
@Aspect
@Component
class LogAspect {

    /**
     * 换行符
     */
    private val LINE_SEPARATOR = System.lineSeparator()

    @Pointcut("execution(public * top.threshold.ktscaffold.controller..*.*(..))")
    fun requestAspect() {
    }

    @Around("requestAspect()")
    fun process(proceedingJoinPoint: ProceedingJoinPoint): Any {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        val request = requestAttributes!!.request
        if (request.requestURL.toString().endsWith("health.do")) {
            return proceedingJoinPoint.proceed()
        }
        // 打印请求相关参数
        log.info("========================================== Start ==========================================")
        // 打印请求 url
        log.info("URL            : {}", request.requestURL.toString())
        // 打印 Http method
        log.info("HTTP Method    : {}", request.method)
        // 打印调用 controller 的全路径以及执行方法
        log.info(
            "Class Method   : {}.{}",
            proceedingJoinPoint.signature.declaringTypeName,
            proceedingJoinPoint.signature.name
        )
        // 打印请求的 IP
        log.info("IP             : {}", request.remoteAddr)
        // 打印请求入参
        log.info("Request Args   : {}", getParams(proceedingJoinPoint))

        val startTime = System.currentTimeMillis()
        val proceed = proceedingJoinPoint.proceed()

        // 打印出参
        log.info("Response Args   : {}", StrUtil.sub(JSONUtil.toJsonStr(proceed), 0, 1024))
        // 执行耗时
        log.info("Time-Consuming  : {} ms", System.currentTimeMillis() - startTime)
        // 接口结束后换行，方便分割查看
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