package com.sparta.order.presentation.controller.docs

import com.sparta.order.presentation.request.CreateOrderRequestDto
import com.sparta.order.presentation.request.ProductInfoRequestDto
import com.sparta.order.presentation.response.OrderResponseDto
import com.sparta.order.presentation.response.Response
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Order", description = "주문 API")
abstract class OrderControllerDocs {

    @Operation(summary = "주문 생성", description = "주문을 생성하는 API 입니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "주문 생성 성공",
                content = [Content(schema = Schema(implementation = Response::class))]
            )
        ]
    )
    @PostMapping("/orders")
    abstract fun createOrder(
        request: HttpServletRequest,
        @RequestBody requestDto: CreateOrderRequestDto,
    ): Response<Long>

    @Operation(summary = "주문 추가 등록", description = "주문을 추가하는 API 입니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "주문 추가 성공",
                content = [Content(schema = Schema(implementation = Response::class))]
            )
        ]
    )
    @PostMapping("/orders/{orderId}")
    abstract fun addOrderDetail(
        @PathVariable orderId: Long,
        @RequestBody requestDto: ProductInfoRequestDto,
    ): Response<Long>

    @Operation(summary = "주문 조회", description = "주문을 조회하는 API 입니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "주문 조회 성공",
                content = [Content(schema = Schema(implementation = Response::class))]
            )
        ]
    )
    @GetMapping("/orders/{orderId}")
    abstract fun getOrder(
        @PathVariable orderId: Long,
    ): Response<OrderResponseDto>
}
