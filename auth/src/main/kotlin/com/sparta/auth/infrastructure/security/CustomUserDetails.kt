package com.sparta.auth.infrastructure.security

import com.sparta.auth.infrastructure.persistence.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val user: User,
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val collection: MutableCollection<GrantedAuthority> = ArrayList()

        collection.add(GrantedAuthority { user.role.name })

        return collection
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.id.toString()
    }
}
