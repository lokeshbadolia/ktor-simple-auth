package com.codexcollab.repo.user.implementation

import com.codexcollab.base.BaseResponse
import com.codexcollab.model.request.RegisterUser

interface UserPresenterImpl {
    suspend fun updateUser(email : String, request: RegisterUser): BaseResponse<Any>
    suspend fun fetchUser(email : String): BaseResponse<Any>
}