package com.codexcollab.repo

import com.codexcollab.base.BaseResponse
import com.codexcollab.encrypt.JwtConfig
import com.codexcollab.model.RegisterAuth
import com.codexcollab.service.AuthService


class AuthRepoUseCase(private val authService: AuthService) : AuthRepo {

    override suspend fun register(request: RegisterAuth): BaseResponse<Any> {
        return if (isEmailExists(request.email)) {
            BaseResponse.Failure(message = "User is already registered")
        } else {
            val user = authService.register(request)
            if (user != null) {
                user.auth_token = JwtConfig.instance.createAuthToken(user.id, user.email)
                BaseResponse.Success(data = user)
            } else {
                BaseResponse.Failure(message = "Something went wrong")
            }
        }
    }

    override suspend fun login(email: String, password: String): BaseResponse<Any> {
        val user = authService.login(email, password)
        return if (user != null) {
            user.auth_token = JwtConfig.instance.createAuthToken(user.id, user.email)
            BaseResponse.Success(data = user, message = "Login Successful")
        } else {
            BaseResponse.Failure(message = "Incorrect credentials, Try again!!")
        }
    }

    private suspend fun isEmailExists(email: String) : Boolean{
        return authService.checkIfAccountExists(email) != null
    }
}