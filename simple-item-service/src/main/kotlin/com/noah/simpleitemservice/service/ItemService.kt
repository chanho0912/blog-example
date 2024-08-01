package com.noah.simpleitemservice.service

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.repository.ItemSearchCommand
import com.noah.simpleitemservice.repository.ItemUpdateCommand

interface ItemService {
    fun save(item: Item): Item

    fun update(itemId: Long, updateCommand: ItemUpdateCommand)

    fun findById(id: Long): Item?

    fun findItems(searchCommand: ItemSearchCommand): List<Item>

}
