package com.unisa.sesalabapi.dto

import com.unisa.sesalab.ods.model.SESALabAccount

data class ProfileDTO(
    val name: String,
    val surname: String,
    val accountType: String,
    val username: String,
    val email: String
)
{
    constructor(user: SESALabAccount): this(
        user.name,
        user.surname,
        user.userType.name,
        user.username,
        user.email
    )
}