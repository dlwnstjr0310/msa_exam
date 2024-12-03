package com.sparta.gateway.exception

import org.springframework.http.HttpStatus

enum class Error(val status: HttpStatus, val message: String) {

    TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "토큰이 만료되었습니다."),
    REGISTERED_IN_BLACK_LIST(HttpStatus.BAD_REQUEST, "블랙리스트에 등록된 토큰입니다."),

}