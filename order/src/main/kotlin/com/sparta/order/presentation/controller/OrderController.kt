package com.sparta.order.presentation.controller

import com.sparta.order.application.service.OrderService
import com.sparta.order.presentation.controller.docs.OrderControllerDocs
import com.sparta.order.presentation.request.CreateOrderRequestDto
import com.sparta.order.presentation.request.ProductInfoRequestDto
import com.sparta.order.presentation.response.OrderResponseDto
import com.sparta.order.presentation.response.Response
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService,
) : OrderControllerDocs() {

    @PostMapping
    override fun createOrder(
        request: HttpServletRequest,
        @RequestBody requestDto: CreateOrderRequestDto,
    ): Response<Long> =
        Response(
            HttpStatus.OK.value(),
            HttpStatus.OK.reasonPhrase,
            orderService.createOrder(request, requestDto)
        )


    @PostMapping("/{orderId}")
    override fun addOrderDetail(
        @PathVariable orderId: Long,
        @RequestBody requestDto: ProductInfoRequestDto,
    ): Response<Long> =
        Response(
            HttpStatus.OK.value(),
            HttpStatus.OK.reasonPhrase,
            orderService.addOrderDetail(orderId, requestDto)
        )


    @GetMapping("/{orderId}")
    override fun getOrder(
        @PathVariable orderId: Long,
    ): Response<OrderResponseDto> =
        Response(
            HttpStatus.OK.value(),
            HttpStatus.OK.reasonPhrase,
            orderService.getOrder(orderId)
        )
}
