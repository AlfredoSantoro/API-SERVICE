package com.unisa.sesalabapi.dto

data class LoginDTO(
    val username: String,
    val password: String
)
{
    constructor(): this(
            "",
            ""
    )
}