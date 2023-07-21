package com.codexcollab.model.response

data class User(
    val id:Int,
    val email:String,
    val name: String? = null,
    val designation: String? = null,
    var authToken:String,
    val last_updated_at:String
)




