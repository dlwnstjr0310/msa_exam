package com.sparta.auth.presentation.api.exception

import org.springframework.http.HttpStatus

enum class Error(val status: HttpStatus, val message: String) {

    TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "토큰이 만료되었습니다."),
    REGISTERED_IN_BLACK_LIST(HttpStatus.BAD_REQUEST, "블랙리스트에 등록된 토큰입니다."),

    INVALID_USERNAME(HttpStatus.BAD_REQUEST, "사용자 이름은 2-10자 사이여야 합니다"),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호는 소문자와 대문자, 특수문자가 포함된 8-20자 사이여야 합니다."),
    INVALID_PASSWORD_CHECK(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    INVALID_PHONE(HttpStatus.BAD_REQUEST, "전화번호 형식이 올바르지 않습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "사용중인 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
}
