package com.sparta.order.presentation.response

import com.sparta.order.infrastructure.persistence.entity.Order

data class OrderResponseDto(
    val id: Long?,
    val productIds: List<Long>?,
)

fun Order.toDto() = OrderResponseDto(
    id = this.id,
    productIds = this.orderDetailList?.map { it.productId }
)
