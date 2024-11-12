package top.threshold.aphrodite.app.aspect

import cn.dev33.satoken.stp.StpUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import top.threshold.aphrodite.pkg.constant.Const
import top.threshold.aphrodite.pkg.helper.RequestDataHelper.clear
import top.threshold.aphrodite.pkg.helper.RequestDataHelper.setRequestData

@Component
class MyInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val userCode = StpUtil.getLoginIdAsString()
        setRequestData(mapOf(Const.CODE to userCode))
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
        clear()
    }
}
