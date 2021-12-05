package com.unisa.sesalabapi.rest

import org.springframework.security.core.context.SecurityContextHolder

open class BaseController
{
    protected fun getUserLogger(): String
    {
        return SecurityContextHolder.getContext().authentication.principal as String
    }
}