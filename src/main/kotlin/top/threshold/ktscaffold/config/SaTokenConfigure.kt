package top.threshold.ktscaffold.config

import cn.dev33.satoken.interceptor.SaInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import top.threshold.ktscaffold.aspect.HmacInterceptor
import top.threshold.ktscaffold.aspect.MDCInterceptor
import top.threshold.ktscaffold.aspect.MyInterceptor

@Configuration
class SaTokenConfigure(
    val mdcInterceptor: MDCInterceptor,
    val myInterceptor: MyInterceptor,
    val hmacInterceptor: HmacInterceptor,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        // Sa-Token的登录状态拦截
        registry.addInterceptor(SaInterceptor())
            .addPathPatterns("/**").excludePathPatterns(common)
        // 检查请求来源合法的hmac拦截
//        registry.addInterceptor(hmacInterceptor)
        registry.addInterceptor(mdcInterceptor)
        // 自定义拦截器
//        registry.addInterceptor(myInterceptor).excludePathPatterns(common)
    }

    companion object {
        private val common =
            listOf(
                "/health.do",
                "/v1/login/**",
            )
    }
}