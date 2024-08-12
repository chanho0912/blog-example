package com.noah.simpleitemservice.service

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.repository.ItemSearchCommand
import com.noah.simpleitemservice.repository.ItemUpdateCommand
import com.noah.simpleitemservice.repository.jpa.asDomainObject
import com.noah.simpleitemservice.repository.jpa.toEntity
import com.noah.simpleitemservice.repository.v2.ItemQueryRepositoryV2
import com.noah.simpleitemservice.repository.v2.ItemRepositoryV2
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ItemServiceV2(
    private val itemRepositoryV2: ItemRepositoryV2,
    private val itemQueryRepositoryV2: ItemQueryRepositoryV2
) : ItemService {
    override fun save(item: Item): Item {
        return itemRepositoryV2.save(item.toEntity()).asDomainObject()
    }

    override fun update(itemId: Long, updateCommand: ItemUpdateCommand) {
        itemRepositoryV2.findByIdOrNull(itemId)?.run {
            val modified = Item(
                id = this.id,
                itemName = updateCommand.itemName,
                price = updateCommand.price,
                quantity = updateCommand.quantity
            )

            itemRepositoryV2.save(modified.toEntity())
        } ?: throw NoSuchElementException()
    }

    override fun findById(id: Long): Item? {
        return itemRepositoryV2.findByIdOrNull(id)?.asDomainObject()
    }

    override fun findItems(searchCommand: ItemSearchCommand): List<Item> {
        return itemQueryRepositoryV2.findAll(searchCommand)
    }
}
