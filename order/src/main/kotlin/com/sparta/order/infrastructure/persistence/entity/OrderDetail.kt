package com.sparta.order.infrastructure.persistence.entity

import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotNull

@Entity
class OrderDetail(
    price: Int,
    quantity: Int,
    productId: Long,
    order: Order? = null,
) : BaseTimeEntity() {

    @NotNull
    val price = price

    @NotNull
    val quantity = quantity

    @NotNull
    val productId = productId

    @NotNull
    @ManyToOne
    @JoinColumn(name = "order_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var order: Order? = order

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun orderInjection(order: Order) {
        this.order = order
    }
}
