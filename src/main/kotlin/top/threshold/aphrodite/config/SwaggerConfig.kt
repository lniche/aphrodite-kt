package top.threshold.aphrodite.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.security.SecurityScheme.In
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("Aphrodite API")
                    .version("1.0.0")
                    .description("API Description")
            )
//            .addSecurityItem(SecurityRequirement().addList("Authorization"))
            .components(
                Components()
                    .addSecuritySchemes(
                        "Authorization", SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .`in`(In.HEADER)
                            .name("Authorization")
                    )
            )
    }
}
