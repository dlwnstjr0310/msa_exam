package com.sparta.auth.presentation.api.request

data class ModifyPasswordRequestDto(

    val email: String,
    val oldPassword: String,
    val newPassword: String,

)
