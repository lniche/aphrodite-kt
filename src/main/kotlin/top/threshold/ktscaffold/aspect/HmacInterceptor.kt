package top.threshold.ktscaffold.aspect

import KtException
import cn.dev33.satoken.stp.StpUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import top.threshold.ktscaffold.entity.Slf4j.Companion.log
import top.threshold.ktscaffold.enums.KtCode
import top.threshold.ktscaffold.util.HmacUtil
import top.threshold.ktscaffold.util.RedisUtil

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
        //当前请求的 uid
        val uid = StpUtil.getLoginIdAsString()
        //请求头里的client info
        val ci = req.getHeader("ci")
        //请求头里的client sign
        val cs = req.getHeader("cs")
        if (null == ci || null == cs) {
            // 未包含 ci 和 cs 时不允许
            valid = false
        }
        if (redisUtil.hasKey("risk:$ci")) {
            //重复请求
            valid = false
        }
        if (!HmacUtil.checkReqHmac(ci, cs, StpUtil.getLoginIdAsString())) {
            // 签名错误
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
            throw KtException(KtCode.FORBIDDEN)
        }
        //每次请求3秒的记录，相同认为重复请求
        redisUtil.setStr("risk:$ci", "1", 3L)
    }
}