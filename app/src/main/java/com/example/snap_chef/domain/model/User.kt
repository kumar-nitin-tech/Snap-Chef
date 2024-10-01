package com.example.snap_chef.domain.model

data class User(
    val name: String,
    val email: String,
    val password: String
){
    constructor():this("","","")
}

data class GoogleUser(
    val uid: String = "",
    val displayName: String? = null,
    val email: String? = null
)
