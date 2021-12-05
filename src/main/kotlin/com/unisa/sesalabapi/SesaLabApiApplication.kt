package com.unisa.sesalabapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EntityScan("com.unisa.sesalab.ods.model")
@ComponentScan("com.unisa.sesalab.ods", "com.unisa.sesalabapi")
class SesaLabApiApplication

fun main(args: Array<String>) {
    runApplication<SesaLabApiApplication>(*args)
}
