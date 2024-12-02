package com.sparta.product.presentation.api.controller

import com.sparta.product.application.service.ProductService
import com.sparta.product.presentation.api.controller.docs.ProductControllerDocs
import com.sparta.product.presentation.api.request.CreateProductRequestDto
import com.sparta.product.presentation.api.response.ProductResponseDto
import com.sparta.product.presentation.api.response.Response
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService,
) : ProductControllerDocs() {

    @PostMapping
    override fun createProduct(
        request: HttpServletRequest,
        @RequestBody requestDto: CreateProductRequestDto,
    ): Response<Long> =
        Response(
            HttpStatus.CREATED.value(),
            HttpStatus.CREATED.reasonPhrase,
            productService.createProduct(request, requestDto)
        )

    @GetMapping
    override fun getProducts(
        @PageableDefault(size = 20) pageable: Pageable,
    ): Response<Page<ProductResponseDto>> =
        Response(
            HttpStatus.OK.value(),
            HttpStatus.OK.reasonPhrase,
            productService.getProducts(pageable)
        )
}