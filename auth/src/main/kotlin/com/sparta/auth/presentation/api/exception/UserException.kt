package com.sparta.auth.presentation.api.exception

open class UserException(val error: Error) : RuntimeException()

class InvalidUsernameException : UserException(Error.INVALID_USERNAME)

class InvalidEmailException : UserException(Error.INVALID_EMAIL)

class InvalidPasswordException : UserException(Error.INVALID_PASSWORD)

class InvalidPasswordCheckException : UserException(Error.INVALID_PASSWORD_CHECK)

class InvalidPhoneException : UserException(Error.INVALID_PHONE)

class EmailAlreadyExistsException : UserException(Error.EMAIL_ALREADY_EXISTS)

class UserNotFoundException : UserException(Error.USER_NOT_FOUND)
