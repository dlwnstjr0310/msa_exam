package com.sparta.product.infrastructure.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
class Product(
    name: String,
    price: Int,
    createdBy: String,
) : BaseTimeEntity() {

    @NotNull
    @Size(min = 2, max = 10)
    val name: String = name

    @NotNull
    val price: Int = price

    @NotNull
    val createdBy: String = createdBy

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
