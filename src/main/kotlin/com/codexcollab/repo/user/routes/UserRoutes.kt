package com.codexcollab.repo.user.routes

import com.codexcollab.model.request.RegisterUser
import com.codexcollab.repo.user.implementation.UserPresenterImpl
import com.codexcollab.security.jwt.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.userRoutes() {

    val user by inject<UserPresenterImpl>()
    val jwt by inject<JwtConfig>()

    routing {
        authenticate {
            route("/user") {
                get("/detail") {
                    val token = jwt.getTokenInfo(call)
                    val result = user.fetchUser(token.email)
                    call.respond(result)
                }
                post("/update") {
                    val token = jwt.getTokenInfo(call)
                    val request = call.receive<RegisterUser>()
                    val result = user.updateUser(token.email, request)
                    call.respond(result)
                }
            }
        }
    }
}