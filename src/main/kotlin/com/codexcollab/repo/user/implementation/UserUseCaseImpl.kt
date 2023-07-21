package com.codexcollab.repo.user.implementation

import com.codexcollab.model.request.RegisterUser
import com.codexcollab.model.response.User

interface UserUseCaseImpl {
    suspend fun updateUser(email: String, user: RegisterUser): User?
    suspend fun fetchUser(email: String): User?
}