package top.threshold.aphrodite.app.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import top.threshold.aphrodite.app.handler.JwtAuthenticationInterceptor
import top.threshold.aphrodite.app.handler.MDCInterceptor

@Configuration
class WebConfig(
    val mdcInterceptor: MDCInterceptor,
    val jwtAuthenticationInterceptor: JwtAuthenticationInterceptor,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(jwtAuthenticationInterceptor).excludePathPatterns(common).excludePathPatterns(static)
        registry.addInterceptor(mdcInterceptor).excludePathPatterns(common).excludePathPatterns(static)
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
                "/api-docs/**",
                "/doc.html",
                "/webjars/**",
            )
    }
}
