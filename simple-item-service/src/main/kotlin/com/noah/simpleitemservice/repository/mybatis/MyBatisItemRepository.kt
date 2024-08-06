package com.noah.simpleitemservice.repository.mybatis

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.ItemSearchCommand
import com.noah.simpleitemservice.repository.ItemUpdateCommand
import org.springframework.stereotype.Repository

@Repository
class MyBatisItemRepository(
    private val itemMapper: ItemMapper
) : ItemRepository {
    override fun save(item: Item): Item {
        itemMapper.save(item)
        return item
    }

    override fun update(itemId: Long, updateCommand: ItemUpdateCommand) {
        itemMapper.update(itemId, updateCommand)
    }

    override fun findById(id: Long): Item? {
        return itemMapper.findById(id)
    }

    override fun findAll(searchCommand: ItemSearchCommand): List<Item> {
        return itemMapper.findAll(searchCommand)
    }
}
