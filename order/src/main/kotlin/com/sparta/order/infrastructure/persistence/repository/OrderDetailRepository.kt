package com.sparta.order.infrastructure.persistence.repository

import com.sparta.order.infrastructure.persistence.entity.OrderDetail
import org.springframework.data.jpa.repository.JpaRepository

interface OrderDetailRepository : JpaRepository<OrderDetail, Long>
