package com.codexcollab.repo

import com.codexcollab.base.BaseResponse
import com.codexcollab.model.RegisterAuth

interface AuthRepo {
    suspend fun register(request: RegisterAuth): BaseResponse<Any>
    suspend fun login(email: String, password: String): BaseResponse<Any>
}