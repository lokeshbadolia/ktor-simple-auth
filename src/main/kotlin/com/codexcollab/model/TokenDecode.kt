package com.codexcollab.model

import io.ktor.server.auth.*

data class TokenDecode(val id: Int) : Principal