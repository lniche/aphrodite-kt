package top.threshold.aphrodite.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    val jwtConfig = environment.config.config("jwt")
    val jwtAudience = jwtConfig.property("audience").getString()
    val jwtIssuer = jwtConfig.property("issuer").getString()
    val jwtSecret = jwtConfig.property("secret").getString()

    val jwtRealm = "Access to the '/protected' path"
    authentication {
        jwt {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.subject != null
                ) JWTPrincipal(credential.payload) else null
            }
        }
    }
}

fun ApplicationCall.getLoginUser(): String {
    val principal = this.principal<JWTPrincipal>()
    return principal!!.payload.subject
}
