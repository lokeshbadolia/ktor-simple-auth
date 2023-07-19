package com.codexcollab

import com.codexcollab.database.DatabaseFactory
import com.codexcollab.encrypt.configureSecurity
import com.codexcollab.repo.AuthRepo
import com.codexcollab.repo.AuthRepoUseCase
import com.codexcollab.routes.authRoute
import com.codexcollab.service.AuthService
import com.codexcollab.service.AuthServiceUseCase
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*


fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1"){
        DatabaseFactory.init()
        install(ContentNegotiation) {
            jackson()
        }
        configureSecurity()
        val authService: AuthService = AuthServiceUseCase()
        val authRepo: AuthRepo = AuthRepoUseCase(authService)
        authRoute(authRepo)
    }.start(wait = true)
}
