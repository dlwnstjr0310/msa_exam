package com.sparta.gateway.exception

open class TokenException(message: String) : RuntimeException(message)

class TokenNotValidException : TokenException("토큰이 유효하지 않습니다.")

class TokenExpiredException : TokenException("토큰이 만료되었습니다.")

class TokenNullException : TokenException("로그인이 필요합니다.")

class PermissionDeniedException : TokenException("권한이 없습니다.")

class RegisteredInBlackListException : TokenException("블랙리스트에 등록된 토큰입니다.")