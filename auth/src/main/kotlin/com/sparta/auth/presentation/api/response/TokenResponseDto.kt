package com.sparta.auth.presentation.api.response

data class TokenResponseDto(
    val tokenType: String,
    val expiresIn: Long,
    val userId: Long,
    val email: String,
    val name: String,
    val role: String,
)
