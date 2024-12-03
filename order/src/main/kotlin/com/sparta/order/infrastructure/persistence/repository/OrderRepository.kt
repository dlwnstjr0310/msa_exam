package com.sparta.order.infrastructure.persistence.repository

import com.sparta.order.infrastructure.persistence.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
//    @EntityGraph(attributePaths = ["orderDetailList"])
//    fun findByIdOrNull(id: Long): Order?
}
