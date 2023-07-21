package com.codexcollab.model.common

import io.ktor.server.auth.*

data class TokenDecode(val id: Int, val email: String) : Principal