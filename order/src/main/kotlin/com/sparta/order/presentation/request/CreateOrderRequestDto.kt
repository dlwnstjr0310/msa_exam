package com.sparta.order.presentation.request

import jakarta.validation.constraints.NotNull

data class CreateOrderRequestDto(

    @NotNull
    val destinationAddr: String,

    @NotNull
    val productInfoList: List<ProductInfoRequestDto>

)
