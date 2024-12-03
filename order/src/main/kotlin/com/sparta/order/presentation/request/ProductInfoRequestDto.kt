package com.sparta.order.presentation.request

import jakarta.validation.constraints.NotNull

data class ProductInfoRequestDto(

    @NotNull
    val id: Long,

    @NotNull
    val quantity: Int,
)
