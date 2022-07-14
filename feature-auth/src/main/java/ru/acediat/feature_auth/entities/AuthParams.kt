package ru.acediat.feature_auth.entities

import java.io.Serializable

data class AuthParams(
    val id : Int,
    val email : String,
    val password : String
) : Serializable