package com.sparta.gateway.filter

import com.sparta.gateway.exception.Error
import com.sparta.gateway.exception.RegisteredInBlackListException
import com.sparta.gateway.exception.TokenExpiredException
import com.sparta.gateway.exception.TokenNotValidException
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
    private val tokenParser: TokenParser,
    private val filterExceptionHandler: FilterExceptionHandler,
) : GlobalFilter, Ordered {

    private val excludedPaths =
        arrayOf("/", "/auth/**", "/auth-server/**", "/product-server/**", "/order-server", "/swagger-ui/**")
    private val pathMatcher = AntPathMatcher()
    private var logger: Logger = Logger.getLogger(javaClass.name)

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {

        val requestPath = exchange.request.uri.path

        if (excludedPaths.any { pathMatcher.match(it, requestPath) }) {
            return chain.filter(exchange)
        }

        val token = tokenParser.extractToken(exchange)

        // TODO: 블랙리스트 확인하기
        return if (token == null) {
            throw TokenNullException()
        } else {
            try {
                tokenParser.validateToken(token)
            } catch (e: TokenExpiredException) {
                return filterExceptionHandler.createErrorResponse(exchange.response, Error.TOKEN_EXPIRED)
            } catch (e: RegisteredInBlackListException) {
                return filterExceptionHandler.createErrorResponse(exchange.response, Error.REGISTERED_IN_BLACK_LIST)
            } catch (e: TokenNotValidException) {
                return filterExceptionHandler.createErrorResponse(exchange.response, Error.TOKEN_NOT_VALID)
            }
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