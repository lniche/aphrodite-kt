package top.threshold.aphrodite.aspect

import cn.dev33.satoken.stp.StpUtil
import cn.hutool.core.map.MapUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import top.threshold.aphrodite.constant.Const
import top.threshold.aphrodite.helper.RequestDataHelper.clear
import top.threshold.aphrodite.helper.RequestDataHelper.setRequestData

@Component
class MyInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val userCode = StpUtil.getLoginIdAsString()
        setRequestData(MapUtil.of(Const.CODE, userCode))
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