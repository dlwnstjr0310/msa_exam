package com.sparta.gateway.filter

import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class GlobalPostFilter : GlobalFilter, Ordered {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        return chain.filter(exchange).then(
            Mono.fromRunnable {
                val port = exchange.request.uri.port
                exchange.response.headers.add("Server-port", port.toString())
            }
        )
    }

    override fun getOrder(): Int {
        return Ordered.LOWEST_PRECEDENCE
    }
}