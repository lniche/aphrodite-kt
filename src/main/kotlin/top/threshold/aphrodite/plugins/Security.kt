package top.threshold.aphrodite.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.util.*

fun Application.configureSecurity() {
    val jwtConfig = environment.config.config("jwt")
    val jwtAudience = jwtConfig.property("audience").getString()
    val jwtDomain = jwtConfig.property("domain ").getString()
    val jwtSecret = jwtConfig.property("secret").getString()

    val jwtRealm = "Access to the '/protected' path"
    authentication {
        jwt {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
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

fun ApplicationCall.generateJWT(userCode: String): String {
    val jwtConfig = this.application.environment.config.config("jwt")
    val jwtAudience = jwtConfig.property("audience").getString()
    val jwtIssuer = jwtConfig.property("issuer").getString()
    val jwtSecret = jwtConfig.property("secret").getString()

    val currentTime = Date()

    val expirationTime = Date(currentTime.time + 24 * 60 * 60 * 1000L)

    return JWT.create()
        .withIssuer(jwtIssuer)
        .withAudience(jwtAudience)
        .withSubject(userCode)
        .withIssuedAt(currentTime)
        .withExpiresAt(expirationTime)
        .sign(Algorithm.HMAC256(jwtSecret))
}
