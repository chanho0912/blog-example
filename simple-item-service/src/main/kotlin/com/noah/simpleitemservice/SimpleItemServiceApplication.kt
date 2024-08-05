package com.noah.simpleitemservice

import com.noah.simpleitemservice.config.JdbcTemplateV2Configuration
import com.noah.simpleitemservice.config.JdbcTemplateV3Configuration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(
    value = [
//        MemoryConfiguration::class,
//        JdbcTemplateV1Configuration::class,
//        JdbcTemplateV2Configuration::class,
        JdbcTemplateV3Configuration::class,
    ]
)
@SpringBootApplication(scanBasePackages = ["com.noah.simpleitemservice.web"])
class SimpleItemServiceApplication {

//    @Bean
//    @Profile("local")
//    fun testDataInit(itemRepository: ItemRepository): TestDataInit {
//        return TestDataInit(itemRepository)
//    }
}

fun main(args: Array<String>) {
    runApplication<SimpleItemServiceApplication>(*args)
}
