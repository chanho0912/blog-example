package com.noah.jpashop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JpaShopApplication

fun main(args: Array<String>) {
    runApplication<JpaShopApplication>(*args)
}