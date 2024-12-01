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
    var email: String = email

    @Size(max = 255)
    var password: String = password

    @NotNull
    @Size(min = 2, max = 10)
    var name: String = name

    @NotNull
    @Size(max = 50)
    @Column(unique = true)
    @Pattern(regexp = CommonConstant.RegExp.PHONE)
    var phone: String = phone

    @Enumerated(EnumType.STRING)
    var role: Role = role

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = id
}

fun User.toDomain() = UserDomain.fromEntity(this)
