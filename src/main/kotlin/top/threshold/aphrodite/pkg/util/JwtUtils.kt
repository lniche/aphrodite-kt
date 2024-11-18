package top.threshold.aphrodite.pkg.util

import io.jsonwebtoken.*
import java.util.*

object JwtUtils {

    private const val SECRET_KEY = "QmFzZTY0IGVuY29kaW5nIGVuY29kaW5nIHNjaGVtZXMgb3ZlciBNSU1F"

    fun generateToken(userCode: String): String {
        return Jwts.builder()
            .subject(userCode)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + 3600 * 24))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact()
    }

    fun getFromToken(token: String): String {
        return parseClaims(token).subject
    }

    fun validateToken(token: String): Boolean {
        return try {
            parseClaims(token)
            true
        } catch (e: SignatureException) {
            false
        } catch (e: MalformedJwtException) {
            false
        } catch (e: ExpiredJwtException) {
            false
        } catch (e: UnsupportedJwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    private fun parseClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .body
    }
}
