package com.unisa.sesalabapi.service

import com.unisa.sesalab.ods.service.user.UserService
import com.unisa.sesalabapi.config.security.UserDetail
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailService(
        private val usersService: UserService
): UserDetailsService
{
    private val logger: Logger = LoggerFactory.getLogger(UserDetailService::class.java)

    override fun loadUserByUsername(username: String): UserDetails
    {
        this.logger.info("### load user in userDetails by username #$username")
        val user = this.usersService.findUserByUsername(username) ?: throw UsernameNotFoundException("$username does not exist")
        return UserDetail(user)
    }

}