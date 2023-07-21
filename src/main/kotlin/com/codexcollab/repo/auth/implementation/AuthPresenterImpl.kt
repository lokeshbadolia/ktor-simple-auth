package com.codexcollab.repo.auth.implementation

import com.codexcollab.base.BaseResponse
import com.codexcollab.model.request.RegisterAuth

interface AuthPresenterImpl {
    suspend fun register(request: RegisterAuth): BaseResponse<Any>
    suspend fun login(email: String, password: String): BaseResponse<Any>
}