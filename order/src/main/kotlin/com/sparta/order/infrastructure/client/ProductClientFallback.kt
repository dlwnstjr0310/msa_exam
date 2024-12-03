package com.sparta.order.infrastructure.client

import com.sparta.order.presentation.exception.GatewayTimeoutException
import com.sparta.order.presentation.response.ProductResponseDto
import org.springframework.stereotype.Component

@Component
class ProductClientFallback : ProductClient {
    override fun getProductList(idList: List<Long>): List<ProductResponseDto> {
        throw GatewayTimeoutException()
    }

    override fun getProduct(productId: Long): ProductResponseDto {
        throw GatewayTimeoutException()
    }
}
