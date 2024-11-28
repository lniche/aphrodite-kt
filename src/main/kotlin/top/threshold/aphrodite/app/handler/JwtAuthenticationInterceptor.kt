package top.threshold.aphrodite.app.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import top.threshold.aphrodite.pkg.constant.Const
import top.threshold.aphrodite.pkg.helper.RequestDataHelper
import top.threshold.aphrodite.pkg.utils.JwtUtils
import kotlin.Throws

@Component
class JwtAuthenticationInterceptor : HandlerInterceptor {

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        var token = request.getHeader("Authorization")

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7)

            if (JwtUtils.validateToken(token)) {
                val userCode = JwtUtils.getFromToken(token)
                RequestDataHelper.setRequestData(mapOf(Const.CODE to userCode))
                return true
            } else {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                return false
            }
        }


        return true
    }

    @Throws(Exception::class)
    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: org.springframework.web.servlet.ModelAndView?
    ) {

    }

    @Throws(Exception::class)
    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {

    }
}
