package com.sparta.auth.infrastructure.persistence.entity

object CommonConstant {

    object RegExp {

        const val EMAIL: String = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
        const val PHONE: String = "^01(?:0|1|[6-9])-(\\d{3}|\\d{4})-\\d{4}\$"
        const val PASSWORD: String = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,20}\$"
    }
}
