package com.codexcollab.encrypt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.codexcollab.model.TokenData
import com.codexcollab.model.TokenDecode
import java.util.*

class JwtConfig private constructor(secret: String) {

    private val algorithm = Algorithm.HMAC256(secret)
    val verifier: JWTVerifier = JWT.require(algorithm).withAudience(AUDIENCE).withIssuer(ISSUER).build()

    fun createAuthToken(id: Int, email: String): String =
        JWT.create().withAudience(AUDIENCE).withIssuer(ISSUER).withClaim(CLAIM, id).withClaim(EMAIL, email)
            .withExpiresAt(Date(System.currentTimeMillis() + 24*60*60000))
            .sign(algorithm)

    fun decodeToken(token : String): TokenData {
        val jwt = JWT.decode(token)
        return TokenData(jwt.getClaim(CLAIM).asInt(), jwt.getClaim(EMAIL).asString())
    }

    companion object {
        private const val ISSUER = "jwt-api-server"
        private const val AUDIENCE = "audience"
        const val CLAIM = "user-id"
        const val EMAIL = "email-id"

        lateinit var instance: JwtConfig
            private set

        fun initialize(secret: String) {
            synchronized(this)
            {
                if (!this::instance.isInitialized) {
                    instance = JwtConfig((secret))
                }
            }
        }
    }
}