package com.sparta.product.presentation.api.request

data class CreateProductRequestDto(
    val name: String,
    val price: Int,
)
