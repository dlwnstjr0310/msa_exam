package com.sparta.auth.infrastructure.persistence.entity

import com.sparta.auth.domain.UserDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@Entity
class User(
    email: String,
    password: String,
    name: String,
    phone: String,
    role: Role,
    id: Long? = null,
) : BaseTimeEntity() {

    @NotNull
    @Size(max = 50)
    @Column(unique = true)
    @Pattern(regexp = CommonConstant.RegExp.EMAIL)
    val email: String = email

    @Size(max = 255)
    val password: String = password

    @NotNull
    @Size(min = 2, max = 10)
    val name: String = name

    @NotNull
    @Size(max = 50)
    @Column(unique = true)
    @Pattern(regexp = CommonConstant.RegExp.PHONE)
    val phone: String = phone

    @Enumerated(EnumType.STRING)
    val role: Role = role

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = id
}

fun User.toDomain() = UserDomain.fromEntity(this)
