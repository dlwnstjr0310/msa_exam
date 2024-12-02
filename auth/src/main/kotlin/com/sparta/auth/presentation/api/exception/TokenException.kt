package com.sparta.auth.presentation.api.exception

open class TokenException(val error: Error) : RuntimeException()

class TokenNotValidException : TokenException(Error.TOKEN_NOT_VALID)

class TokenExpiredException : TokenException(Error.TOKEN_EXPIRED)

class RegisteredInBlackListException : TokenException(Error.REGISTERED_IN_BLACK_LIST)
