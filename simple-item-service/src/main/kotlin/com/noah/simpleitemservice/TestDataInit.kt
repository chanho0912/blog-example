package com.noah.simpleitemservice

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.repository.ItemRepository
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener

class TestDataInit(
    private val itemRepository: ItemRepository
) {
    private val logger = KotlinLogging.logger {}

    @EventListener(ApplicationReadyEvent::class)
    fun init() {
        logger.info { "data init" }
        itemRepository.save(Item(itemName = "itemA", price = 10000, quantity = 10))
        itemRepository.save(Item(itemName = "itemB", price = 20000, quantity = 20))
    }
}
