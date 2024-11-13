package top.threshold.aphrodite.app.handler

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import top.threshold.aphrodite.pkg.constant.Const
import top.threshold.aphrodite.pkg.helper.RequestDataHelper
import top.threshold.aphrodite.pkg.util.JwtUtils

@Component
class JwtAuthenticationInterceptor : HandlerInterceptor {

    // 请求到达控制器方法之前调用
    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // 获取请求头中的Authorization字段
        var token = request.getHeader("Authorization")

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7) // 去掉 "Bearer " 前缀

            // 验证 token 是否有效
            if (JwtUtils.validateToken(token)) {
                // 如果有效，设置用户信息（你可以根据需要将用户信息放到请求上下文中）
                val userCode = JwtUtils.getFromToken(token)
                // 这里可以将用户名或其他信息设置到请求上下文中，比如：
                RequestDataHelper.setRequestData(mapOf(Const.CODE to userCode))
                return true // 继续请求的处理
            } else {
                // 如果无效，返回401 Unauthorized响应
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                return false // 阻止请求继续向下执行
            }
        }

        // 如果没有Token或Token无效，允许请求继续，后续控制器处理
        return true
    }

    // 视图渲染之后调用（不需要做任何处理，可以不实现）
    @Throws(Exception::class)
    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: org.springframework.web.servlet.ModelAndView?
    ) {
        // 可选：对响应数据进行处理
    }

    // 请求完成后调用（用于清理操作）
    @Throws(Exception::class)
    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        // 可选：清理资源等操作
    }
}
