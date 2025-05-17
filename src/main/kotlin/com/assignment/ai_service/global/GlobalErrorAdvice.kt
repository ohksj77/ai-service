package com.assignment.ai_service.global

import com.assignment.ai_service.global.dto.ErrorResponse
import com.assignment.ai_service.global.exception.NotInitializedException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalErrorAdvice {

    private val logger = LoggerFactory.getLogger(GlobalErrorAdvice::class.java)

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotInitializedException::class)
    fun handleNotInitializedException(e: NotInitializedException): ErrorResponse {
        logger.error("NotInitializedException: {}", e.message)
        return ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleMethodArgumentNotValidException(e: IllegalArgumentException): ErrorResponse {
        logger.error("MethodArgumentNotValidException: {}", e.message)
        return ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.message!!)
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(IllegalStateException::class)
    fun handleEntityNotFoundException(e: IllegalStateException): ErrorResponse {
        logger.error("EntityNotFoundException: {}", e.message)
        return ErrorResponse(HttpStatus.BAD_GATEWAY.value(), e.message!!)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ErrorResponse {
        logger.error("Exception: {}", e.message)
        return ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.message ?: "Unknown error")
    }
}
