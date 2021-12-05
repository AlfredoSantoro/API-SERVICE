package com.unisa.sesalabapi.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/hello")
class HelloWorldController
{
    @GetMapping
    fun sayHello(): ResponseEntity<String> { return ResponseEntity("This is a simple test of a GET request", HttpStatus.OK) }
}