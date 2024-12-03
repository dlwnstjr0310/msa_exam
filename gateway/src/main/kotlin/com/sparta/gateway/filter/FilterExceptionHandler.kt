package com.sparta.gateway.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.sparta.gateway.Response
import com.sparta.gateway.exception.Error
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets

@Component
class FilterExceptionHandler {

    fun createErrorResponse(response: ServerHttpResponse, error: Error): Mono<Void> {

        response.headers.contentType = MediaType.APPLICATION_JSON
        response.headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

        val customResponse: Response<Unit> = Response(
            error.status.value(),
            error.message,
        )


        val json = ObjectMapper().writeValueAsString(customResponse)
        val bytes = json.toByteArray(StandardCharsets.UTF_8)
        val buffer = response.bufferFactory().wrap(bytes)

        return response.writeWith(Mono.just(buffer))
    }

}