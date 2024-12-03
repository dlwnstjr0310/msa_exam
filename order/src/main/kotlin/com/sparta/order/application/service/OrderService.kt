package com.sparta.order.application.service

import com.sparta.order.infrastructure.client.ProductClient
import com.sparta.order.infrastructure.persistence.entity.Order
import com.sparta.order.infrastructure.persistence.entity.OrderDetail
import com.sparta.order.infrastructure.persistence.repository.OrderDetailRepository
import com.sparta.order.infrastructure.persistence.repository.OrderRepository
import com.sparta.order.presentation.exception.NotFoundOrderException
import com.sparta.order.presentation.request.CreateOrderRequestDto
import com.sparta.order.presentation.request.ProductInfoRequestDto
import com.sparta.order.presentation.response.OrderResponseDto
import com.sparta.order.presentation.response.toDto
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderService(
    private val productClient: ProductClient,
    private val orderRepository: OrderRepository,
    private val orderDetailRepository: OrderDetailRepository,
) {

    fun createOrder(request: HttpServletRequest, requestDto: CreateOrderRequestDto): Long? {

        val username = request.getHeader("X-Authenticated-User")

        val idList = requestDto.productInfoList.map { it.id }
        val productList = productClient.getProductList(idList)

        val productMap = productList.associateBy({ it.id }, { it })

        val orderDetailList: List<OrderDetail> = requestDto.productInfoList.map {
            productMap[it.id]?.let { product ->
                OrderDetail(
                    product.price,
                    it.quantity,
                    product.id,
                )
            }!!
        }

        val totalPrice = orderDetailList.sumOf { it.price * it.quantity }

        val order = Order(
            requestDto.destinationAddr,
            username.toLong(),
            totalPrice,
        )

        orderDetailList.forEach { it.orderInjection(order) }
        order.orderDetailListInjection(orderDetailList.toMutableList())

        return orderRepository.save(order).id
    }

    fun addOrderDetail(orderId: Long, requestDto: ProductInfoRequestDto): Long? {

        val order = orderRepository.findByIdOrNull(orderId) ?: throw NotFoundOrderException()
        val product = productClient.getProduct(requestDto.id)

        val orderDetail = OrderDetail(
            product.price,
            requestDto.quantity,
            product.id,
            order
        )

        val price = product.price * requestDto.quantity
        order.updateOrderDetailListAndTotalPrice(orderDetail, price)

        return orderRepository.save(order).id
    }

    @Transactional(readOnly = true)
    fun getOrder(orderId: Long): OrderResponseDto {
        return orderRepository.findByIdOrNull(orderId)?.toDto() ?: throw NotFoundOrderException()
    }

}
