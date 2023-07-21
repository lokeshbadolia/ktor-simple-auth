package com.codexcollab.base

import io.ktor.http.*

sealed class BaseResponse<T> {
    data class Success<T>(val statusCode: HttpStatusCode = HttpStatusCode.OK, val data: T? = null, val message: String? = null) : BaseResponse<T>()
    data class Failure<T>(val statusCode: HttpStatusCode = HttpStatusCode.OK, val data: T? = null, val message: String? = null) : BaseResponse<T>()
}