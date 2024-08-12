package com.noah.simpleitemservice.config

import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.jpa.JpaItemRepositoryV3
import com.noah.simpleitemservice.service.ItemService
import com.noah.simpleitemservice.service.ItemServiceV1
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueryDslConfiguration {
    @Bean
    fun itemService(itemRepository: ItemRepository): ItemService =
        ItemServiceV1(itemRepository)

    @Bean
    fun itemRepository(em: EntityManager): ItemRepository =
        JpaItemRepositoryV3(em)
}
