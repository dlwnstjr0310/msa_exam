package com.sparta.auth.domain

import com.sparta.auth.infrastructure.persistence.entity.CommonConstant
import com.sparta.auth.infrastructure.persistence.entity.User
import com.sparta.auth.presentation.api.exception.InvalidEmailException
import com.sparta.auth.presentation.api.exception.InvalidPasswordException
import com.sparta.auth.presentation.api.exception.InvalidPhoneException
import com.sparta.auth.presentation.api.exception.InvalidUsernameException

class UserDomain private constructor(
    val id: Long? = 0,
    private val email: String,
    private val password: String,
    private val name: String,
    private val phone: String,
) {

    val userEmail: String
        get() = email
    val userName: String
        get() = name
    val userPhone: String
        get() = phone
    val userPassword: String
        get() = password

    companion object {
        fun create(
            email: String,
            rawPassword: String,
            name: String,
            phone: String,
        ): UserDomain {
            validateInput(email, rawPassword, name, phone)

            return UserDomain(
                email = email,
                password = rawPassword,
                name = name,
                phone = phone
            )
        }

        private fun validateInput(
            email: String,
            password: String,
            name: String,
            phone: String,
        ) {
            validateEmail(email)
            validateName(name)
            validatePassword(password)
            validatePhone(phone)
        }

        private fun validateEmail(email: String) {
            if (!email.matches(CommonConstant.RegExp.EMAIL.toRegex())) {
                throw InvalidEmailException()
            }
        }

        private fun validateName(name: String) {
            if (name.length !in 2..10) {
                throw InvalidUsernameException()
            }
        }

        private fun validatePassword(password: String) {
            if (!password.matches(CommonConstant.RegExp.PASSWORD.toRegex())) {
                throw InvalidPasswordException()
            }
        }

        private fun validatePhone(phone: String) {
            if (!phone.matches(CommonConstant.RegExp.PHONE.toRegex())) {
                throw InvalidPhoneException()
            }
        }

        fun fromEntity(user: User): UserDomain {
            return UserDomain(
                id = user.id,
                email = user.email,
                password = user.password,
                name = user.name,
                phone = user.phone
            )
        }
    }

    fun changePassword(newPassword: String): UserDomain {
        validatePassword(newPassword)

        return UserDomain(
            id = this.id,
            email = this.email,
            password = newPassword,
            name = this.name,
            phone = this.phone
        )
    }
}
