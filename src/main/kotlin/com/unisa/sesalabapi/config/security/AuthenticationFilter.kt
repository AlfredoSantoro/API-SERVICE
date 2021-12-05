package com.unisa.sesalabapi.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.unisa.sesalabapi.dto.LoginDTO
import com.unisa.sesalabapi.dto.ResponseLoginDTO
import com.unisa.sesalabapi.utils.JwtManager
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
        private val loginUrl: String,
        private val authManager: AuthenticationManager,
        private val param: String,
        private val prefix: String,
        private val secret: String
): AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(loginUrl, "POST"))
{
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication
    {
        val loginDTO = ObjectMapper().readValue(request.inputStream, LoginDTO::class.java)
        this.logger.info("### loginDTO forwarded #${loginDTO.username}")
        return this.authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginDTO.username,
                        loginDTO.password,
                        emptyList()
                ))
    }

    override fun successfulAuthentication(request: HttpServletRequest,
                                          response: HttpServletResponse,
                                          chain: FilterChain,
                                          authResult: Authentication)
    {
        val user = authResult.principal as UserDetail
        // there will surely exist only one authority for the SESALabAccount
        val authority = user.authorities.first().authority
        val accessToken = JwtManager.createJwt(user.username, this.secret, authority)
        response.addHeader(this.param, "${this.prefix} $accessToken")
        response.addHeader("Content-Type", "application/json")
        val jsonResponse = ObjectMapper().writeValueAsString(ResponseLoginDTO(user.username, accessToken, authority))
        this.logger.info("### successfulAuthentication for #${user.username}")
        response.writer.write(jsonResponse)
    }

}