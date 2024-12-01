package com.sparta.auth.presentation.api.request

data class SignInRequestDto(
    val email: String,
    val password: String,
)
