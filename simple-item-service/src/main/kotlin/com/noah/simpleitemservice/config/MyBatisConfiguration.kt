package com.noah.simpleitemservice.config

import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.mybatis.ItemMapper
import com.noah.simpleitemservice.repository.mybatis.MyBatisItemRepository
import com.noah.simpleitemservice.service.ItemService
import com.noah.simpleitemservice.service.ItemServiceV1
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MyBatisConfiguration {
    @Bean
    fun itemService(itemRepository: ItemRepository): ItemService =
        ItemServiceV1(itemRepository)

    @Bean
    fun itemRepository(itemMapper: ItemMapper): ItemRepository =
        MyBatisItemRepository(itemMapper)
}
