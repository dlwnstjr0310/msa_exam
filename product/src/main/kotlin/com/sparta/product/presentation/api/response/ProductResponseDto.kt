package com.sparta.product.presentation.api.response

import com.sparta.product.infrastructure.persistence.entity.Product

data class ProductResponseDto(

    val id: Long,
    val name: String,
    val price: Int,

    )

fun Product.toSummaryResponseDto(): ProductResponseDto {
    return ProductResponseDto(
        id = id!!,
        name = name,
        price = price,
    )
}
