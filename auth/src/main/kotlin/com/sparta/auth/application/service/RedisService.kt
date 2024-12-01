package com.sparta.auth.application.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, Any>,
) {

    private val blackListKey = "blackList"

    fun storeRefreshToken(userId: String, refreshToken: String, tokenExpireTimeMillis: Long) {
        redisTemplate.opsForValue().set(userId, refreshToken, tokenExpireTimeMillis, TimeUnit.MILLISECONDS)
    }

    fun getRefreshToken(userId: String): String? {
        return redisTemplate.opsForValue().get(userId) as String?
    }

    fun deleteRefreshToken(userId: String, tokenExpireTimeMillis: Long) {
        redisTemplate.opsForValue().get(userId)?.let {
            storeInBlackList(it, tokenExpireTimeMillis)
            redisTemplate.delete(userId)
        }
    }

    fun storeInBlackList(token: Any, tokenExpireTimeMillis: Long) {
        redisTemplate.opsForValue().set(blackListKey + token, token, tokenExpireTimeMillis, TimeUnit.MILLISECONDS)
    }

    fun isBlackList(token: String): Boolean {
        return redisTemplate.hasKey(blackListKey + token)
    }
}
