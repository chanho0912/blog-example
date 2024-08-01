package com.noah.simpleitemservice.service

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.ItemSearchCommand
import com.noah.simpleitemservice.repository.ItemUpdateCommand
import org.springframework.stereotype.Service

@Service
class ItemServiceV1(
    private val itemRepository: ItemRepository
) : ItemService {
    override fun save(item: Item): Item {
        return itemRepository.save(item)
    }

    override fun update(itemId: Long, updateCommand: ItemUpdateCommand) {
        itemRepository.update(itemId, updateCommand)
    }

    override fun findById(id: Long): Item? {
        return itemRepository.findById(id)
    }

    override fun findItems(searchCommand: ItemSearchCommand): List<Item> {
        return itemRepository.findAll(searchCommand)
    }
}
