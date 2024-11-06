package top.threshold.aphrodite.app.aspect

import KtException
import cn.dev33.satoken.stp.StpUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import top.threshold.aphrodite.app.entity.Slf4j.Companion.log
import top.threshold.aphrodite.pkg.enums.Errors
import top.threshold.aphrodite.pkg.util.HmacUtil
import top.threshold.aphrodite.pkg.util.RedisUtil

@Component
class HmacInterceptor(
    val redisUtil: RedisUtil,
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (StpUtil.isLogin()) {
            checkHmac(request)
        }
        return true
    }

    private fun checkHmac(req: HttpServletRequest) {
        var valid = true
        val uid = StpUtil.getLoginIdAsString()
        val ci = req.getHeader("ci")
        val cs = req.getHeader("cs")
        if (null == ci || null == cs) {
            valid = false
        }
        if (redisUtil.hasKey("risk:$ci")) {
            valid = false
        }
        if (!HmacUtil.checkReqHmac(ci, cs, StpUtil.getLoginIdAsString())) {
            valid = false
        }
        if (!valid) {
            log.warn(
                "HmacInterceptor拦截异常请求:uid[{}], uri[{}], ci[{}], cs[{}]",
                uid,
                req.requestURI,
                ci,
                cs
            )
            throw KtException(Errors.ERR_FORBIDDEN)
        }
        redisUtil.setStr("risk:$ci", "1", 3L)
    }
}