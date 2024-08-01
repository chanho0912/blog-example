package com.noah.simpleitemservice.repository.memory

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.ItemSearchCommand
import com.noah.simpleitemservice.repository.ItemUpdateCommand

class InMemoryItemRepository : ItemRepository {
    override fun save(item: Item): Item {
        item.id = ++sequence
        store[item.id] = item
        return item
    }

    override fun update(itemId: Long, updateCommand: ItemUpdateCommand) {
        findById(itemId)
            ?.run {
                val updated = this.copy(
                    itemName = updateCommand.itemName,
                    price = updateCommand.price,
                    quantity = updateCommand.quantity
                )
                store.remove(itemId)
                store[itemId] = updated
            }
            ?: throw NoSuchElementException("no item with itemId=$itemId")

    }

    override fun findById(id: Long): Item? {
        return store[id]
    }

    override fun findAll(searchCommand: ItemSearchCommand): List<Item> {
        return with(searchCommand) {
            store.values.filter { item ->
                item.itemName.contains(this.itemName)
            }.filter { item ->
                maxPrice == null || item.price <= maxPrice
            }
        }
    }

    companion object {
        val store: MutableMap<Long, Item> = mutableMapOf()
        var sequence = 0L
    }
}
