package com.sparta.product.application.service

import com.sparta.product.infrastructure.persistence.entity.Product
import com.sparta.product.infrastructure.persistence.repository.ProductRepository
import com.sparta.product.presentation.api.request.CreateProductRequestDto
import com.sparta.product.presentation.api.response.ProductResponseDto
import com.sparta.product.presentation.api.response.toSummaryResponseDto
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductService(
    private val productRepository: ProductRepository,
) {

    fun createProduct(request: HttpServletRequest, requestDto: CreateProductRequestDto): Long {
        val product = Product(
            name = requestDto.name,
            price = requestDto.price,
            createdBy = request.getHeader("X-Authenticated-User")
        )
        return productRepository.save(product).id!!
    }

    @Transactional(readOnly = true)
    fun getProducts(pageable: Pageable): Page<ProductResponseDto> {
        val products = productRepository.findAll(pageable)

        return PageImpl(
            products.content.map { it.toSummaryResponseDto() },
            products.pageable,
            products.totalElements
        )
    }

}
