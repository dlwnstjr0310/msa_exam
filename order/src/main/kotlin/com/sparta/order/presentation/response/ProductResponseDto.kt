package com.sparta.order.presentation.response

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDto(
    val id: Long,
    val name: String,
    val price: Int,
)
