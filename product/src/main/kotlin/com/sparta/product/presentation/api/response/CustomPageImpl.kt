package com.sparta.product.presentation.api.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import kotlinx.serialization.Serializable
import org.springframework.data.redis.core.RedisHash

@RedisHash
@Serializable
data class CustomPageImpl(
    @JsonProperty("content")
    @JsonDeserialize(contentAs = ProductResponseDto::class)
    val content: List<ProductResponseDto>? = emptyList(),
    @JsonProperty("page")
    val page: Int? = 0,
    @JsonProperty("size")
    val size: Int? = 0,
    @JsonProperty("totalElements")
    val totalElements: Long? = 0,
) : java.io.Serializable
