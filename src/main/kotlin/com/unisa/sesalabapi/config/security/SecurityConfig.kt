package com.unisa.sesalabapi.config.security

import com.unisa.sesalabapi.service.UserDetailService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

/**
 * Spring Security Config
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
        private val userDetailService: UserDetailService,
): WebSecurityConfigurerAdapter()
{
    private val loginUrl = "/api/v1/login"
    private val signUpUrl = "/api/v1/account/sign-up"

    @Value("\${sesalabidentity.jwt.secret}")
    private lateinit var secret: String

    @Value("\${sesalabidentity.jwt.prefix}")
    private lateinit var prefix: String

    @Value("\${sesalabidentity.jwt.param}")
    private lateinit var param: String

    @Bean
    fun corsFilter(): FilterRegistrationBean<CorsFilter>
    {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration().applyPermitDefaultValues()
        config.addAllowedMethod(HttpMethod.POST)
        config.addAllowedMethod(HttpMethod.GET)
        config.addAllowedMethod(HttpMethod.PUT)
        config.addAllowedMethod(HttpMethod.DELETE)
        config.addAllowedMethod(HttpMethod.PATCH)
        source.registerCorsConfiguration("/**", config)
        val bean =  FilterRegistrationBean(CorsFilter(source))
        bean.order = Ordered.HIGHEST_PRECEDENCE
        return bean
    }

    override fun configure(http: HttpSecurity)
    {
        http.cors().disable()
                .csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers(HttpMethod.POST, this.signUpUrl).permitAll()
                .antMatchers(HttpMethod.POST, this.loginUrl).permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/hello").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(AuthenticationFilter(this.loginUrl, authenticationManager(), this.param, this.prefix, this.secret), UsernamePasswordAuthenticationFilter::class.java)
                .addFilterBefore(AuthorizationFilter(authenticationManager(), this.param, this.prefix, this.secret), UsernamePasswordAuthenticationFilter::class.java)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    override fun configure(auth: AuthenticationManagerBuilder)
    {
        auth.userDetailsService(this.userDetailService)
                .passwordEncoder(Sha256PasswordEncoder())
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource
    {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }
}