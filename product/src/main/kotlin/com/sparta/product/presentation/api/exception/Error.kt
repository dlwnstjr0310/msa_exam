package com.sparta.product.presentation.api.exception

import org.springframework.http.HttpStatus

enum class Error(val status: HttpStatus, val message: String) {

    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),

    CIRCUIT_BREAKER_OPEN(HttpStatus.BAD_REQUEST, "이용량 증가로 현재 서비스가 불가능합니다. 잠시 후에 다시 요청해주세요."),
    GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "응답 시간을 초과하였습니다."),

}
