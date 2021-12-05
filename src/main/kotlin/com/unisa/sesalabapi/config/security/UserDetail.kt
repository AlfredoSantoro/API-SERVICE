package com.unisa.sesalabapi.config.security

import com.unisa.sesalab.ods.model.SESALabAccount
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetail(
        private val user: SESALabAccount
): UserDetails
{
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>
    {
        val authority = GrantedAuthority { this.user.userType.name }
        return mutableListOf(authority)
    }

    override fun getPassword(): String { return this.user.encodedPassword }

    override fun getUsername(): String { return this.user.username }

    override fun isAccountNonExpired(): Boolean { return true }

    override fun isAccountNonLocked(): Boolean { return true }

    override fun isCredentialsNonExpired(): Boolean { return true }

    override fun isEnabled(): Boolean { return true }
}