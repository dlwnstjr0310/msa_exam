package com.sparta.auth.presentation.api.exception

import com.sparta.auth.presentation.api.response.Response
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@Hidden
@RestControllerAdvice
class CommonExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException::class)
    fun handleUserException(ex: UserException): Response<Unit> {
        return Response(ex.error.status.value(), ex.error.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TokenException::class)
    fun handleTokenException(ex: TokenException): Response<Unit> {
        return Response(ex.error.status.value(), ex.error.message)
    }
}
