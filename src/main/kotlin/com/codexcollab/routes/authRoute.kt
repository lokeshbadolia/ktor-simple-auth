package com.codexcollab.routes

import com.codexcollab.model.RegisterAuth
import com.codexcollab.repo.AuthRepo
import io.ktor.server.application.*
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
    }
}