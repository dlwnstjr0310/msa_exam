package com.sparta.auth.infrastructure.security

import com.sparta.auth.infrastructure.persistence.entity.User
import com.sparta.auth.presentation.api.exception.TokenExpiredException
import com.sparta.auth.presentation.api.exception.TokenNotValidException
import com.sparta.auth.presentation.api.response.TokenResponseDto
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil(
    @Value("\${security.jwt.key}")
    private val key: String,

    ) {

    private val issuer = "ME"
    private val tokenType = "Bearer "
    private val authorityKey = "auth"
    private val accessTokenExpireTimeMillis = 60L * 60L * 1000L
    private var secretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(key))

    fun extractToken(request: HttpServletRequest): String {
        val token = request.getHeader(HttpHeaders.AUTHORIZATION) ?: throw TokenNotValidException()

        if (token.startsWith("Bearer ")) {
            val jwt = token.substring(7)
            return jwt
        } else {
            throw TokenNotValidException()
        }
    }

    fun validateToken(token: String) {
        try {
            val secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(key))

            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
        } catch (e: ExpiredJwtException) {
            throw TokenExpiredException()
        } catch (e: Exception) {
            throw TokenNotValidException()
        }
    }

    fun getUsername(token: String): String {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }

    fun generateToken(user: User, expDate: Long): String {
        return Jwts.builder()
            .issuer(issuer)
            .subject(user.id.toString())
            .expiration(Date(System.currentTimeMillis() + expDate))
            .claim(authorityKey, user.role.name)
            .signWith(secretKey)
            .compact()
    }

    fun generateTokenResponseDto(user: User): TokenResponseDto {
        return TokenResponseDto(
            tokenType = tokenType,
            expiresIn = accessTokenExpireTimeMillis,
            userId = user.id!!,
            email = user.email,
            name = user.name,
            role = user.role.name
        )
    }
}
