package com.noah.simpleitemservice.config

import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.memory.InMemoryItemRepository
import com.noah.simpleitemservice.service.ItemService
import com.noah.simpleitemservice.service.ItemServiceV1
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MemoryConfiguration {

    @Bean
    fun itemService(itemRepository: ItemRepository): ItemService =
        ItemServiceV1(itemRepository)

    @Bean
    fun itemRepository(): ItemRepository =
        InMemoryItemRepository()
}
