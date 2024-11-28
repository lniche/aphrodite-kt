package top.threshold.aphrodite.app.controller

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import top.threshold.aphrodite.pkg.constant.Const
import top.threshold.aphrodite.pkg.helper.RequestDataHelper
import top.threshold.aphrodite.pkg.utils.JwtUtils

abstract class BaseController {

    protected fun login(userCode: String): String {
        return JwtUtils.generateToken(userCode)
    }

    protected fun loginUid(): String {
        return RequestDataHelper.getParam(Const.CODE) ?: ""
    }

    protected val realIpAddress: String
        get() {
            val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
            return request.getHeader("realIpAddress") ?: "127.0.0.1"
        }

}
