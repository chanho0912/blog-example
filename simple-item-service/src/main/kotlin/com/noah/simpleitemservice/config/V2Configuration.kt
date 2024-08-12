package com.noah.simpleitemservice.config

import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.jpa.JpaItemRepositoryV3
import com.noah.simpleitemservice.repository.v2.ItemQueryRepositoryV2
import com.noah.simpleitemservice.repository.v2.ItemRepositoryV2
import com.noah.simpleitemservice.service.ItemService
import com.noah.simpleitemservice.service.ItemServiceV2
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.beans

@Configuration
class V2Configuration(
    private val em: EntityManager
) {

    @Bean
    fun itemQueryRepositoryV2(
        em: EntityManager
    ): ItemQueryRepositoryV2 =
        ItemQueryRepositoryV2(em)

    @Bean
    fun itemService(
        itemRepository: ItemRepositoryV2,
        itemQueryRepositoryV2: ItemQueryRepositoryV2
    ): ItemService =
        ItemServiceV2(itemRepository, itemQueryRepositoryV2)

    // Test 깨져서 우선 추가.
    @Bean
    fun itemRepository(em: EntityManager): ItemRepository =
        JpaItemRepositoryV3(em)
}
