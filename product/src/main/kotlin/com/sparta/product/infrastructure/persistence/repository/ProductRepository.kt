package com.sparta.product.infrastructure.persistence.repository

import com.sparta.product.infrastructure.persistence.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>
