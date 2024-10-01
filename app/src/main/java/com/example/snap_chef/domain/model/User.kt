package com.example.snap_chef.domain.model

data class User(
    val name: String,
    val email: String,
    val password: String
){
    constructor():this("","","")
}
