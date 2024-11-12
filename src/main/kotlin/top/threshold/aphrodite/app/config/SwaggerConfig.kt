package top.threshold.aphrodite.app.config

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
        Server(description = "Development Environment", url = "http://127.0.0.1:8000"),
        Server(description = "Test Environment", url = "http://test.aphrodite.com")
    ]
)
@Configuration
class SwaggerConfig {
    @Bean
    fun customOpenAPI(): OpenAPI {
        val openAPI = OpenAPI()
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
        return openAPI
    }

    @Bean("appV1GroupApi")
    fun appV1GroupApi(): GroupedOpenApi {
        return GroupedOpenApi.builder().group("API V1 Docs")
            .pathsToMatch("/v1/**")
            .build()
    }

}
