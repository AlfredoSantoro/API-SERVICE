package com.unisa.sesalabapi.config.security

import com.unisa.sesalabapi.utils.JwtManager
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * This class handles the token validation of an authenticated user
 */
class AuthorizationFilter(
        private val authManager: AuthenticationManager,
        private val param: String,
        private val prefix: String,
        private val secret: String
): BasicAuthenticationFilter(authManager)
{
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain)
    {
        val headerAuth = request.getHeader(this.param)
        if ( headerAuth !== null && headerAuth.startsWith(this.prefix) )
        {
            this.logger.info("### access_token in header request")
            val token = headerAuth.replace("${this.prefix} ", "")
            val decoded = JwtManager.verifyJwt(token, this.secret)
            val authority = decoded.getClaim("authority").asString()
            SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(decoded.subject, null, listOf(GrantedAuthority { authority }))
            this.logger.info("### valid token")
        }
        chain.doFilter(request, response)
    }
}