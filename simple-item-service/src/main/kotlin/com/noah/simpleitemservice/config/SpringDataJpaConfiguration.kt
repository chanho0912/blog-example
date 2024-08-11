package com.noah.simpleitemservice.config

import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.jpa.JpaItemRepositoryV2
import com.noah.simpleitemservice.repository.jpa.SpringDataJpaItemRepository
import com.noah.simpleitemservice.service.ItemService
import com.noah.simpleitemservice.service.ItemServiceV1
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringDataJpaConfiguration {
    @Bean
    fun itemService(itemRepository: ItemRepository): ItemService =
        ItemServiceV1(itemRepository)

    @Bean
    fun itemRepository(repository: SpringDataJpaItemRepository): ItemRepository =
        JpaItemRepositoryV2(repository)
}
