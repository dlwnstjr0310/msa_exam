package com.sparta.order.infrastructure.client

import com.sparta.order.presentation.response.ProductResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "product-server", fallback = ProductClientFallback::class)
interface ProductClient {

    @GetMapping("/products/orders")
    fun getProductList(@RequestParam idList: List<Long>): List<ProductResponseDto>

    @GetMapping("/products/{productId}")
    fun getProduct(@PathVariable productId: Long): ProductResponseDto
}
