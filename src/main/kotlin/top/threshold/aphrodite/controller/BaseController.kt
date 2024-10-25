package top.threshold.aphrodite.controller

import cn.dev33.satoken.stp.StpUtil
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes


abstract class BaseController {
    private val isLogin: Boolean
        get() = StpUtil.isLogin()

    protected fun login(uid: String?): String {
        StpUtil.login(uid)
        return StpUtil.getTokenValue()
    }

    protected fun loginUid(): String {
        return StpUtil.getLoginIdAsString()
    }


    protected val realIpAddress: String
        get() {
            val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
            return request.getHeader("realIpAddress") ?: "127.0.0.1"
        }

}