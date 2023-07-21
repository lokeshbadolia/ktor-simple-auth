package com.codexcollab.repo.auth.gateway

import com.codexcollab.model.response.Auth
import com.codexcollab.model.request.RegisterAuth

interface AuthUseCaseImpl {
    suspend fun login(email: String, password: String): Auth?
    suspend fun register(register: RegisterAuth): Auth?
    suspend fun checkIfAccountExists(email: String): Auth?
}