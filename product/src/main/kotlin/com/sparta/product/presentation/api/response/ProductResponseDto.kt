package com.sparta.product.presentation.api.response

import com.sparta.product.infrastructure.persistence.entity.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDto(
    val id: Long? = 0,
    val name: String? = "",
    val price: Int? = 0,
)

fun Product.toResponseDto() = ProductResponseDto(
    id = id!!,
    name = name,
    price = price
)
