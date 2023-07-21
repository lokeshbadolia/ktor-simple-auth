package com.codexcollab.routes

import com.codexcollab.repo.auth.routes.authRoutes
import com.codexcollab.repo.user.routes.userRoutes
import io.ktor.server.application.*

fun Application.setupRoutes() {
    this.authRoutes()
    this.userRoutes()
}