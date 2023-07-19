package com.codexcollab.service

import com.codexcollab.model.Auth
import com.codexcollab.model.RegisterAuth

interface AuthService {
    suspend fun login(email: String, password: String): Auth?
    suspend fun register(register: RegisterAuth): Auth?
    suspend fun checkIfAccountExists(email: String): Auth?
}