package com.noah.simpleitemservice

import com.noah.simpleitemservice.config.JdbcTemplateV1Configuration
import com.noah.simpleitemservice.config.MemoryConfiguration
import com.noah.simpleitemservice.repository.ItemRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile

@Import(
    value = [
//        MemoryConfiguration::class,
        JdbcTemplateV1Configuration::class,
    ]
)
@SpringBootApplication(scanBasePackages = ["com.noah.simpleitemservice.web"])
class SimpleItemServiceApplication {

    @Bean
    @Profile("local")
    fun testDataInit(itemRepository: ItemRepository): TestDataInit {
        return TestDataInit(itemRepository)
    }
}

fun main(args: Array<String>) {
    runApplication<SimpleItemServiceApplication>(*args)
}
