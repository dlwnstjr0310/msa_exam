package com.sparta.gateway.util

import com.sparta.gateway.exception.TokenExpiredException
import com.sparta.gateway.exception.TokenNotValidException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import javax.crypto.SecretKey

@Component
class TokenParser(
    @Value("\${security.jwt.key}")
    private val key: String,
    private val redisService: RedisService,
) {

    private val TOKEN_TYPE = "Bearer "
    private val AUTHORITY_KEY = "auth"
    private var secretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(key))

    fun extractToken(exchange: ServerWebExchange): String? {
        val token = exchange.request.headers.getFirst("Authorization")

        return if (token != null && token.startsWith("Bearer "))
            token.substring(7)
        else null
    }

    fun validateToken(token: String) {
        try {

            val secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(key))

            val claimsJws = Jwts.parser()
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

}