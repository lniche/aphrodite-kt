package top.threshold.aphrodite.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.security.SecurityScheme.In
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@OpenAPIDefinition(
    servers = [
        Server(description = "Development Environment", url = "http://localhost:8000"),
        Server(description = "Test Environment", url = "https://test.aphrodite.com")
    ]
)
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

    @Bean("appGroupApi")
    fun appGroupApi(): GroupedOpenApi {
        return GroupedOpenApi.builder().group("App Module Group")
            .pathsToMatch("/v1/**")
            .build()
    }

}
