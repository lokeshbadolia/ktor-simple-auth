package com.codexcollab.repo.user.presenter

import com.codexcollab.base.BaseResponse
import com.codexcollab.model.request.RegisterUser
import com.codexcollab.repo.user.implementation.UserPresenterImpl
import com.codexcollab.repo.user.implementation.UserUseCaseImpl
import io.ktor.http.*


class UserPresenter(private val useCase: UserUseCaseImpl) : UserPresenterImpl {

    override suspend fun updateUser(email: String, request: RegisterUser): BaseResponse<Any> {
        return if (request.name == null || request.name == ""){
            BaseResponse.Failure(statusCode = HttpStatusCode.BadRequest, message = "Name cannot be empty.")
        }else if (request.designation == null || request.designation == ""){
            BaseResponse.Failure(statusCode = HttpStatusCode.BadRequest, message = "Designation cannot be empty.")
        }else{
            val user = useCase.updateUser(email, request)
            return if (user != null) {
                BaseResponse.Success(data = user, message = "User updated successfully.")
            } else {
                BaseResponse.Failure(statusCode = HttpStatusCode.BadRequest, message = "User Not Found!!")
            }
        }
    }

    override suspend fun fetchUser(email: String): BaseResponse<Any> {
        val user = useCase.fetchUser(email)
        return if (user != null) {
            BaseResponse.Success(data = user, message = "User detail fetched.")
        } else {
            BaseResponse.Failure(statusCode = HttpStatusCode.BadRequest, message = "User Not Found!!")
        }
    }
}