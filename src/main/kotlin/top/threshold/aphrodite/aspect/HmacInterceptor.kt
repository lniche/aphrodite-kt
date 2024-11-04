package top.threshold.aphrodite.aspect

import KtException
import cn.dev33.satoken.stp.StpUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import top.threshold.aphrodite.entity.Slf4j.Companion.log
import top.threshold.aphrodite.enums.KtCode
import top.threshold.aphrodite.utils.HmacUtil
import top.threshold.aphrodite.utils.RedisUtil

@Component
class HmacInterceptor(
    val redisUtil: RedisUtil,
) : HandlerInterceptor {

    /**
     * 当hmac拦截打开，且请求包含登录用户信息是，检查请求头中的 client info（ci） 和 sign（cs）
     * @param request
     * @return
     */
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
            throw KtException(KtCode.ERR_FORBIDDEN)
        }
        redisUtil.setStr("risk:$ci", "1", 3L)
    }
}