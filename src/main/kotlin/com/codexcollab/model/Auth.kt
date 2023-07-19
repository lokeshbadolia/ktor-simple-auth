package com.codexcollab.model

data class Auth(
    val id:Int,
    val email: String,
    val createdAt:String,
    var auth_token: String = ""
)




