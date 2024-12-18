package com.sparta.product

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
class ProductApplication

fun main(args: Array<String>) {
    runApplication<ProductApplication>(*args)
}
