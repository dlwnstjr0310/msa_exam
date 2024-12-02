package com.sparta.auth.presentation.api.exception;

import com.sparta.auth.presentation.api.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.sparta.auth"})
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public Response<Void> handleUserException(UserException e) {
        return new Response<>(
            e.getError().getStatus().value(),
            e.getError().getMessage(),
            null
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TokenException.class)
    public Response<Void> handleTokenException(TokenException e) {
        return new Response<>(
            e.getError().getStatus().value(),
            e.getError().getMessage(),
            null
        );
    }
}
