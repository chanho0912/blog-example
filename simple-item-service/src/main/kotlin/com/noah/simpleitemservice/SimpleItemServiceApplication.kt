package com.noah.simpleitemservice

import com.noah.simpleitemservice.config.JdbcTemplateV2Configuration
import com.noah.simpleitemservice.config.JdbcTemplateV3Configuration
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

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

    private val logger = KotlinLogging.logger {}

//    @Bean
//    @Profile("local")
//    fun testDataInit(itemRepository: ItemRepository): TestDataInit {
//        return TestDataInit(itemRepository)
//    }

//    @Bean
//    @Profile("test")
//    fun dataSource(): DataSource {
//        logger.info { "Memory Database Init" }
//        val ds = DriverManagerDataSource()
//        ds.apply {
//            setDriverClassName("org.h2.Driver")
//            url = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"
//            username = "sa"
//            password = ""
//        }
//
//        return ds
//    }
}

fun main(args: Array<String>) {
    runApplication<SimpleItemServiceApplication>(*args)
}
