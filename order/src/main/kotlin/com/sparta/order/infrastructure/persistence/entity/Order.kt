package com.sparta.order.infrastructure.persistence.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.NotNull

@Entity
class Order(
    destinationAddr: String,
    userId: Long,
    totalPrice: Int,
    orderDetailList: List<OrderDetail>? = emptyList(),
) : BaseTimeEntity() {

    @NotNull
    val destinationAddr = destinationAddr

    @NotNull
    val userId = userId

    @NotNull
    var totalPrice = totalPrice
        protected set

    @NotNull
    @OneToMany(
        mappedBy = "order",
        orphanRemoval = true,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE]
    )
    var orderDetailList: MutableList<OrderDetail>? = orderDetailList?.toMutableList()
        protected set

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun orderDetailListInjection(orderDetailList: MutableList<OrderDetail>) {
        this.orderDetailList = orderDetailList
    }

    fun updateOrderDetailListAndTotalPrice(orderDetail: OrderDetail, totalPrice: Int) {
        this.orderDetailList?.add(orderDetail)
        this.totalPrice += totalPrice
    }
}
