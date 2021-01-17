package com.ricardocosta.template.exceptions

import com.ricardocosta.template.dto.ErrorDTO
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlers {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(NotImplementedError::class)
    fun handleNotImplementedError(error: NotImplementedError): ResponseEntity<ErrorDTO> {
        logger.warn { error }

        return ResponseEntity(
            ErrorDTO("E0000", message = "Not implemented yet."),
            HttpStatus.METHOD_NOT_ALLOWED
        )
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exception: RuntimeException): ResponseEntity<ErrorDTO> {
        logger.warn { exception }

        return ResponseEntity(
            ErrorDTO("E0001", message = "Unexpected error has occurred."),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}
