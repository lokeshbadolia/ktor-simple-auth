package com.codexcollab.application

import com.codexcollab.database.connection.DatabaseFactory
import com.codexcollab.koin.authModules
import com.codexcollab.koin.jwtModule
import com.codexcollab.koin.userModules
import com.codexcollab.routes.setupRoutes
import com.codexcollab.security.jwt.setupJwtValidator
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0"){
        install(Koin) {
            slf4jLogger()
            modules(listOf(jwtModule, authModules, userModules))
        }
        DatabaseFactory.init()
        install(ContentNegotiation) { jackson() }
        setupJwtValidator()
        setupRoutes()
    }.start(wait = true)
}
