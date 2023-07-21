package com.codexcollab.security.jwt

import com.codexcollab.model.common.TokenDecode
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.ktor.ext.inject


fun Application.setupJwtValidator() {

    val jwt by inject<JwtConfig>()

    install(Authentication) {
        jwt {
            verifier(jwt.verifier)
            validate {
                val id = it.payload.getClaim(JWT_USER_ID).asInt()
                val email = it.payload.getClaim(JWT_USER_EMAIL).asString()
                if (id != null && email != null) { TokenDecode(id, email) } else null
            }
        }
    }
}