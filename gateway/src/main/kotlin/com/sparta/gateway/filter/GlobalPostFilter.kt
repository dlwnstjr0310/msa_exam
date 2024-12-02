package com.sparta.gateway.filter

import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.cloud.gateway.route.Route
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.net.URI

@Component
class GlobalPostFilter : GlobalFilter, Ordered {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {

        return chain.filter(exchange).then(
            Mono.fromRunnable {
                val route = exchange.getAttribute<Route>(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR)
                val uri = route?.uri
                val servicePort = when {
                    uri?.scheme == "lb" -> {
                        exchange.getAttribute<URI>(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR)?.port
                    }

                    else -> uri?.port
                }

                exchange.response.headers.add("Service-Port", servicePort.toString())
            }
        )
    }

    override fun getOrder(): Int {
        return Ordered.LOWEST_PRECEDENCE
    }
}