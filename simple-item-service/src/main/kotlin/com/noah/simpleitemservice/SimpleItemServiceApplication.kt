package com.noah.simpleitemservice

import com.noah.simpleitemservice.config.JpaConfiguration
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(
    value = [
//        MemoryConfiguration::class,
//        JdbcTemplateV1Configuration::class,
//        JdbcTemplateV2Configuration::class,
//        JdbcTemplateV3Configuration::class,
//        MyBatisConfiguration::class,
        JpaConfiguration::class
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
