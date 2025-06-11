package top.threshold.aphrodite.app.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import top.threshold.aphrodite.pkg.constant.Const
import java.util.*

@Component
class MDCInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        var traceId = request.getHeader(Const.TRACE_ID)
        if (traceId == null) {
            traceId = UUID.randomUUID().toString().replace("-", "")
        }
        response.setHeader(Const.TRACE_ID, traceId)
        MDC.put(Const.TRACE_ID, traceId)
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
    }


    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        MDC.remove(Const.TRACE_ID)
    }
}
