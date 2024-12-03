package com.sparta.product.presentation.api.controller.docs

import com.sparta.product.presentation.api.request.CreateProductRequestDto
import com.sparta.product.presentation.api.response.CustomPageImpl
import com.sparta.product.presentation.api.response.ProductResponseDto
import com.sparta.product.presentation.api.response.Response
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Product", description = "상품 API")
abstract class ProductControllerDocs {

    @Operation(summary = "상품 등록", description = "상품을 등록하는 API 입니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "상품 등록 성공",
                content = [Content(schema = Schema(implementation = Response::class))]
            )
        ]
    )
    @PostMapping("/products")
    abstract fun createProduct(
        request: HttpServletRequest,
        @RequestBody requestDto: CreateProductRequestDto,
    ): Response<Long>

    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회하는 API 입니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "상품 목록 조회 성공",
                content = [Content(schema = Schema(implementation = Response::class))]
            )
        ]
    )
    @GetMapping("/products")
    abstract fun getProducts(
        @PageableDefault(size = 20) pageable: Pageable,
    ): Response<CustomPageImpl>

    @Operation(summary = "상품 조회", description = "주어진 ID 로 해당하는 상품 목록을 조회하는 API 입니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "상품 목록 조회 성공",
                content = [Content(schema = Schema(implementation = Response::class))]
            )
        ]
    )
    @GetMapping("/products/orders")
    abstract fun getProductList(
        @RequestParam idList: List<Long>,
    ): List<ProductResponseDto>

    @Operation(summary = "상품 조회", description = "주어진 ID 로 해당하는 상품을 조회하는 API 입니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "상품 조회 성공",
                content = [Content(schema = Schema(implementation = Response::class))]
            )
        ]
    )
    @GetMapping("/products/{productId}")
    abstract fun getProduct(
        @PathVariable productId: Long,
    ): ProductResponseDto
}
