package com.sparta.gateway.filter

import com.sparta.gateway.exception.TokenNullException
import com.sparta.gateway.util.TokenParser
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.logging.Logger

@Component
class GatewayHeaderFilter(
    private val tokenParser: TokenParser
) : GlobalFilter, Ordered {

    private val excludedPaths = arrayOf("/", "/auth/**", "/auth-server/**", "/product-server/**", "/swagger-ui/**")
    private val pathMatcher = AntPathMatcher()
    private var logger: Logger = Logger.getLogger(javaClass.name)

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {

        val requestPath = exchange.request.uri.path

        if (excludedPaths.any { pathMatcher.match(it, requestPath) }) {
            return chain.filter(exchange)
        }

        val token = tokenParser.extractToken(exchange)

        // TODO: 어떻게 할지 나중에 정하기
        // 블랙리스트도 확인하기
        // 아마.. 토큰 만료시에는 refreshToken 을 통해 재발급하고 다시 넣어주기?
        // 응답 핸들러 만들어야함
        return if (token == null) {
            throw TokenNullException()
        } else {
            tokenParser.validateToken(token)
            chain.filter(
                exchange.mutate()
                    .request(
                        exchange.request.mutate()
                            .header("X-Authenticated-User", tokenParser.getUsername(token))
                            .build()
                    )
                    .build()
            )
        }
    }

    override fun getOrder(): Int {
        return Ordered.HIGHEST_PRECEDENCE
    }

}