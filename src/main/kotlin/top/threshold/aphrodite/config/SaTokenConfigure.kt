package top.threshold.aphrodite.config

import cn.dev33.satoken.interceptor.SaInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import top.threshold.aphrodite.aspect.HmacInterceptor
import top.threshold.aphrodite.aspect.MDCInterceptor
import top.threshold.aphrodite.aspect.MyInterceptor

@Configuration
class SaTokenConfigure(
    val mdcInterceptor: MDCInterceptor,
    val myInterceptor: MyInterceptor,
    val hmacInterceptor: HmacInterceptor,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(SaInterceptor())
            .addPathPatterns("/**").excludePathPatterns(common).excludePathPatterns(static)
//        registry.addInterceptor(hmacInterceptor).excludePathPatterns(common)
        registry.addInterceptor(mdcInterceptor).excludePathPatterns(common).excludePathPatterns(static)
        registry.addInterceptor(myInterceptor).excludePathPatterns(common).excludePathPatterns(static)
    }

    companion object {
        private val common =
            listOf(
                "/",
                "/ping",
                "/v1/login",
                "/v1/send-code",
            )
        private val static =
            listOf(
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/api-docs/**",
                "/doc.html",
                "/webjars/**",
            )
    }
}