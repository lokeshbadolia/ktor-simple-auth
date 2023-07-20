package com.codexcollab.encrypt

import com.codexcollab.model.TokenDecode
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*


fun Application.configureSecurity() {
    JwtConfig.initialize("ktorsimpleauth")
    install(Authentication) {
        jwt {
            verifier(JwtConfig.instance.verifier)
            validate {
                val id = it.payload.getClaim(JwtConfig.CLAIM).asInt()
                //val email = it.payload.getClaim(JwtConfig.EMAIL).asString()
                if (id != null) {
                    //JWTPrincipal(it.payload)
                    TokenDecode(id)
                } else null
            }
        }
    }
}