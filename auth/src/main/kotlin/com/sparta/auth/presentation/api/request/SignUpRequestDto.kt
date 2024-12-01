package com.sparta.auth.presentation.api.request

data class SignUpRequestDto(
    val email: String,
    val password: String,
    val name: String,
    val phone: String,
)
