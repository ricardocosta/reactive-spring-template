package com.ricardocosta.template

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TemplateApplication

fun main(vararg args: String) {
    runApplication<TemplateApplication>(*args)
}
