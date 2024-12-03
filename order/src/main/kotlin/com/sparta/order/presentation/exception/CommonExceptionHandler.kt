package com.sparta.order.presentation.exception

import com.sparta.order.presentation.response.Response
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@Hidden
@RestControllerAdvice
class CommonExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServerException::class)
    fun handleServerException(ex: ServerException): Response<Unit> {
        return Response(ex.error.status.value(), ex.error.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OrderException::class)
    fun handleOrderException(ex: OrderException): Response<Unit> {
        return Response(ex.error.status.value(), ex.error.message)
    }

}
