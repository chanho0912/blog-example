package com.noah.simpleitemservice

import com.noah.simpleitemservice.config.V2Configuration
import com.noah.simpleitemservice.repository.v2.ItemQueryRepositoryV2
import com.noah.simpleitemservice.repository.v2.ItemRepositoryV2
import com.noah.simpleitemservice.service.ItemService
import com.noah.simpleitemservice.service.ItemServiceV2
import jakarta.persistence.EntityManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.context.support.beans

@Import(
    value = [
//        MemoryConfiguration::class,
//        JdbcTemplateV1Configuration::class,
//        JdbcTemplateV2Configuration::class,
//        JdbcTemplateV3Configuration::class,
//        MyBatisConfiguration::class,
//        JpaConfiguration::class,
//        SpringDataJpaConfiguration::class,
//        QueryDslConfiguration::class,
//        V2Configuration::class
    ]
)
@SpringBootApplication(scanBasePackages = ["com.noah.simpleitemservice.web"])
class SimpleItemServiceApplication {

//    private val logger = KotlinLogging.logger {}

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
    runApplication<SimpleItemServiceApplication>(*args) {
        addInitializers(
            beans {
                bean<ItemQueryRepositoryV2>() {
                    ItemQueryRepositoryV2(ref<EntityManager>())
                }

                bean<ItemService>() {
                    ItemServiceV2(
                        itemRepositoryV2 = ref<ItemRepositoryV2>(),
                        itemQueryRepositoryV2 = ref<ItemQueryRepositoryV2>(),
                    )
                }
            }
        )
    }
}
