package com.codexcollab.repo.auth.routes

import com.codexcollab.model.request.RegisterAuth
import com.codexcollab.repo.auth.implementation.AuthPresenterImpl
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.authRoutes() {

    val auth by inject<AuthPresenterImpl>()

    routing {
        route("/auth") {
            post("/register") {
                val request = call.receive<RegisterAuth>()
                val result = auth.register(request)
                call.respond(result)
            }
            post("/login") {
                val request = call.receive<RegisterAuth>()
                val result = auth.login(request.email, request.password)
                call.respond(result)
            }
        }
        /*swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "4.15.5"
        }*/
    }
}