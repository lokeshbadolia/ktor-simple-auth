package com.codexcollab.repo.auth.presenter

import com.codexcollab.base.BaseResponse
import com.codexcollab.model.request.RegisterAuth
import com.codexcollab.repo.auth.gateway.AuthPresenterImpl
import com.codexcollab.repo.auth.gateway.AuthUseCaseImpl
import io.ktor.http.*


class AuthPresenter(private val authPresenterImpl: AuthUseCaseImpl) : AuthPresenterImpl {
    override suspend fun register(request: RegisterAuth): BaseResponse<Any> {
        return if (isEmailExists(request.email)) {
            BaseResponse.Failure(message = "User is already registered")
        } else {
            val auth = authPresenterImpl.register(request)
            if (auth != null) {
                BaseResponse.Success(data = auth, message = "User register successfully.")
            } else {
                BaseResponse.Failure(statusCode = HttpStatusCode.BadRequest, message = "Something went wrong")
            }
        }
    }

    override suspend fun login(email: String, password: String): BaseResponse<Any> {
        val auth = authPresenterImpl.login(email, password)
        return if (auth != null) {
            BaseResponse.Success(data = auth, message = "Login Successful")
        } else {
            BaseResponse.Failure(message = "Incorrect credentials, Try again!!")
        }
    }

    private suspend fun isEmailExists(email: String) : Boolean{
        return authPresenterImpl.checkIfAccountExists(email) != null
    }
}