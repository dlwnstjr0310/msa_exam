package com.sparta.order.presentation.exception

open class ServerException(val error: Error) : RuntimeException()

class CircuitBreakerOpenException : ServerException(Error.CIRCUIT_BREAKER_OPEN)

class GatewayTimeoutException : ServerException(Error.CIRCUIT_BREAKER_OPEN)