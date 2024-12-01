package com.sparta.auth.infrastructure.persistence.repository

import com.sparta.auth.infrastructure.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): User?
}
