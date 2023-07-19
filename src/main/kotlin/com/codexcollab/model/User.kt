package com.codexcollab.model

data class User(
    val id:Int,
    val userId:Int,
    val name: String,
    val designation: String,
    var authToken:String?=null,
    val lastUpdatedAt:String
)




