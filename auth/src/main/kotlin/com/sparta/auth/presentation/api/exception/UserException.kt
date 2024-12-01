package com.sparta.auth.presentation.api.exception

open class UserException(message: String) : RuntimeException(message)

class InvalidUsernameException : UserException("사용자 이름은 2-10자 사이여야 합니다")

class InvalidEmailException : UserException("이메일 형식이 올바르지 않습니다.")

class InvalidPasswordException : UserException("비밀번호는 소문자와 대문자, 특수문자가 포함된 8-20자 사이여야 합니다.")

class InvalidPasswordCheckException : UserException("비밀번호가 일치하지 않습니다.")

class InvalidPhoneException : UserException("전화번호 형식이 올바르지 않습니다.")

class UserNotFoundException : UserException("사용자를 찾을 수 없습니다.")

class EmailAlreadyExistsException : UserException("사용중인 이메일입니다.")
