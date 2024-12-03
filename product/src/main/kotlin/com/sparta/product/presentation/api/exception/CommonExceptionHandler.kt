package com.sparta.product.presentation.api.exception

import com.sparta.product.presentation.api.response.Response
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@Hidden
@RestControllerAdvice
class CommonExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductException::class)
    fun handleProductException(ex: ProductException): Response<Unit> {
        return Response(ex.error.status.value(), ex.error.message)
    }

}
