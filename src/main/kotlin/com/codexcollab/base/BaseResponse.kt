package com.codexcollab.base

import io.ktor.http.*

sealed class BaseResponse<T> (val statusCode: HttpStatusCode = HttpStatusCode.OK) {
    data class Success<T>(val data: T? = null, val message: String? = null) : BaseResponse<T>()
    data class Failure<T>(val data: T? = null, val message: String? = null) : BaseResponse<T>()
}