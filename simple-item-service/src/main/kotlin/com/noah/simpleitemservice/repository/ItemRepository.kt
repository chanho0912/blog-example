package com.noah.simpleitemservice.repository

import com.noah.simpleitemservice.domain.Item

interface ItemRepository {
    fun save(item: Item): Item
    fun update(itemId: Long, updateCommand: ItemUpdateCommand)
    fun findById(id: Long): Item?
    fun findAll(searchCommand: ItemSearchCommand): List<Item>
}
