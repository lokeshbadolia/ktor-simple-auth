package com.codexcollab.routes

import com.codexcollab.encrypt.JwtConfig
import com.codexcollab.model.RegisterAuth
import com.codexcollab.repo.AuthRepo
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.authRoute(repo: AuthRepo) {
    routing {
        route("/auth") {
            post("/register") {
                val request = call.receive<RegisterAuth>()
                val result = repo.register(request)
                call.respond(result.statusCode, result)
            }
            post("/login") {
                val request = call.receive<RegisterAuth>()
                val result = repo.login(request.email, request.password)
                call.respond(result.statusCode, result)
            }
        }

        authenticate {
            get("/detail") {
                val authHeader = call.request.parseAuthorizationHeader()
                val token = JwtConfig.instance.decodeToken(authHeader.toString().replace("Bearer ", ""))
                call.respond("Token Decoded as : "+token.id + " || " + token.email)
            }
        }

        /*swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "4.15.5"
        }*/
    }
}