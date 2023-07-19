package com.codexcollab.encrypt

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*


fun Application.configureSecurity() {
    JwtConfig.initialize("ktorsimpleauth")
    authentication {
        jwt {
            verifier(JwtConfig.instance.verifier)
            validate {
                val claim = it.payload.getClaim(JwtConfig.CLAIM).asInt()
                if (claim != null) UserIdPrincipalForUser(claim)
                else null
            }
        }
    }
}