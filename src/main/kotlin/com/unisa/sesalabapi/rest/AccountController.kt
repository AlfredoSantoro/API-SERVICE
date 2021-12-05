package com.unisa.sesalabapi.rest

import com.unisa.sesalab.ods.service.user.UserService
import com.unisa.sesalabapi.dto.ProfileDTO
import development.kit.exception.IllegalAccountException
import development.kit.user.CreateAccount
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/account")
class AccountController(
    private val userService: UserService
): BaseController()
{
    private val baseUrl = "/api/v1/account"
    private val logger: Logger = LoggerFactory.getLogger(AccountController::class.java)

    @PostMapping("/sign-up")
    fun signUpUser(@RequestBody createAccount: CreateAccount): ResponseEntity<HttpStatus>
    {
        this.logger.info("### Received POST request ${this.baseUrl}/sign-up")
        val newAccount = this.userService.signUpUser(createAccount)
        this.logger.info("### account #${newAccount.username} successfully registered")
        return ResponseEntity<HttpStatus>(HttpStatus.OK)
    }

    @GetMapping("/me")
    fun getProfileInformation(): ResponseEntity<ProfileDTO>
    {
        this.logger.info("### Received GET request ${this.baseUrl}/me")
        val user = this.userService.findUserByUsername(this.getUserLogger())
        return if ( user !== null ) { ResponseEntity.ok(ProfileDTO(user)) }
        else throw IllegalAccountException("Account not found")
    }
}