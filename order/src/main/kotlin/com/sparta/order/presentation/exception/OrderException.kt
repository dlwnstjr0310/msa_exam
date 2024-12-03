package com.sparta.order.presentation.exception

open class OrderException(val error: Error) : RuntimeException()

class NotFoundOrderException : OrderException(Error.NOT_FOUND_ORDER)
