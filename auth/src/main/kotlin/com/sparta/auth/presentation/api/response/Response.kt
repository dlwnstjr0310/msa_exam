package com.sparta.auth.presentation.api.response

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Response<T>(
    val code: Int? = HttpStatus.OK.value(),
    val message: String? = HttpStatus.OK.reasonPhrase,
    val data: Any? = null,
)