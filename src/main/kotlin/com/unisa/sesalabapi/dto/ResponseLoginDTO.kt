package com.unisa.sesalabapi.dto

data class ResponseLoginDTO(
    val username: String,
    val token: String,
    val accountType: String
)