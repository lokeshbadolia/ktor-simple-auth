package com.codexcollab.encrypt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

class JwtConfig private constructor(secret: String) {

    private val algorithm = Algorithm.HMAC256(secret)
    val verifier: JWTVerifier = JWT.require(algorithm).withIssuer(ISSUER).withAudience(AUDIENCE).build()

    fun createAuthToken(id: Int, email: String): String =
        JWT.create().withIssuer(ISSUER).withClaim(CLAIM, id).withClaim(EMAIL, email).sign(algorithm)

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