package com.sparta.gateway.util

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    private val blackListKey = "blackList"

    fun findRefreshTokenInRedis(userId: String): Any? {
        return redisTemplate.opsForValue().get(userId)
    }

    fun isBlackList(token: String): Boolean {
        return redisTemplate.hasKey(blackListKey + token)
    }

}