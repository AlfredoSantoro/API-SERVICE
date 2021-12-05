package com.unisa.sesalabapi.config.security

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.security.crypto.password.PasswordEncoder

class Sha256PasswordEncoder: PasswordEncoder
{
    override fun encode(rawPassword: CharSequence): String { return DigestUtils.sha256Hex(rawPassword.toString()) }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean
    {
        return this.encode(rawPassword) == encodedPassword
    }
}