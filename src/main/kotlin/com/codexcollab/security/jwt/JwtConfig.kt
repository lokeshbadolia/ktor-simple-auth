package com.codexcollab.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.codexcollab.model.common.TokenData
import io.ktor.server.application.*
import io.ktor.server.auth.*
import java.util.*

private const val ISSUER = "jwt-api-server"
private const val AUDIENCE = "audience"
const val JWT_USER_ID = "user-id"
const val JWT_USER_EMAIL = "email-id"

class JwtConfig(secret: String) {

    private val algorithm = Algorithm.HMAC256(secret)
    val verifier: JWTVerifier = JWT.require(algorithm).withAudience(AUDIENCE).withIssuer(ISSUER).build()

    fun createAuthToken(id: Int, email: String): String =
        JWT.create().withAudience(AUDIENCE).withIssuer(ISSUER).withClaim(JWT_USER_ID, id).withClaim(JWT_USER_EMAIL, email)
            .withExpiresAt(Date(System.currentTimeMillis() + 24*60*60000))
            .sign(algorithm)

    private fun decodeToken(token : String): TokenData {
        val jwt = JWT.decode(token)
        return TokenData(jwt.getClaim(JWT_USER_ID).asInt(), jwt.getClaim(JWT_USER_EMAIL).asString())
    }

    fun getTokenInfo(call: ApplicationCall) : TokenData {
        val authHeader = call.request.parseAuthorizationHeader()
        val token = decodeToken(authHeader.toString().replace("Bearer ", ""))
        return TokenData(token.id, token.email)
    }
}