package com.ricardocosta.template.controllers

import com.ricardocosta.template.dto.HelloDTO
import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello", consumes = [MediaType.APPLICATION_JSON_VALUE])
class HelloController {
    private val logger = KotlinLogging.logger {}

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun hello(@RequestBody helloDTO: HelloDTO): String {
        logger.debug { "POST /hello was called with $helloDTO" }

        throw NotImplementedError("TO DO")
    }
}
