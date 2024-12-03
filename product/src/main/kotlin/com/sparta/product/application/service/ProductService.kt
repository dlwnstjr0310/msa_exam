package com.sparta.product.application.service

import com.sparta.product.infrastructure.persistence.entity.Product
import com.sparta.product.infrastructure.persistence.repository.ProductRepository
import com.sparta.product.presentation.api.exception.NotFoundProductException
import com.sparta.product.presentation.api.request.CreateProductRequestDto
import com.sparta.product.presentation.api.response.CustomPageImpl
import com.sparta.product.presentation.api.response.ProductResponseDto
import com.sparta.product.presentation.api.response.toResponseDto
import jakarta.servlet.http.HttpServletRequest
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
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
    @Cacheable(cacheNames = ["products"], key = "args[0]")
    fun getProducts(pageable: Pageable): CustomPageImpl {
        val products = productRepository.findAll(pageable)

        val content: List<ProductResponseDto> = products.content.map { it.toResponseDto() }

        println(content)
        val customPageImpl = CustomPageImpl(
            content = content,
            page = products.number,
            size = products.size,
            totalElements = products.totalElements
        )
        return customPageImpl
    }

    @Transactional(readOnly = true)
    fun getProductList(idList: List<Long>): List<ProductResponseDto> {
        return productRepository.findAllById(idList).map { it.toResponseDto() }
    }

    @Transactional(readOnly = true)
    fun getProduct(id: Long): ProductResponseDto {
        return productRepository.findByIdOrNull(id)?.toResponseDto() ?: throw NotFoundProductException()
    }
}
