package com.codexcollab.model.response

data class Auth(
    val id:Int,
    val email: String,
    val created_at:String,
    var auth_token: String? = null
)




