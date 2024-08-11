package com.noah.simpleitemservice.repository.jpa

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.domain.ItemEntity
import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.ItemSearchCommand
import com.noah.simpleitemservice.repository.ItemUpdateCommand
import com.noah.simpleitemservice.repository.JpaLikePatternUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils

@Repository
class JpaItemRepositoryV2(
    private val repository: SpringDataJpaItemRepository
) : ItemRepository {
    override fun save(item: Item): Item {
        return repository.save(item.toEntity()).asDomainObject()
    }

    override fun update(itemId: Long, updateCommand: ItemUpdateCommand) {
        val entity: ItemEntity = repository.findByIdOrNull(itemId) ?: throw NoSuchElementException("itemId: $itemId")

        val updated = entity.asDomainObject()
            .copy(
                itemName = updateCommand.itemName,
                price = updateCommand.price,
                quantity = updateCommand.quantity
            )
            .toEntity()
        repository.save(updated)
    }

    override fun findById(id: Long): Item? {
        return repository.findByIdOrNull(id)?.asDomainObject()
    }

    override fun findAll(searchCommand: ItemSearchCommand): List<Item> {
        val itemName = searchCommand.itemName
        val maxPrice = searchCommand.maxPrice

        val items =
            when {
                StringUtils.hasText(itemName) && maxPrice != null -> repository.findItems(
                    JpaLikePatternUtils.fullMatch(
                        itemName!!
                    ), maxPrice
                )

                StringUtils.hasText(itemName) -> repository.findByItemNameLike(JpaLikePatternUtils.fullMatch(itemName!!))
                maxPrice != null -> repository.findByPriceLessThanEqual(maxPrice)
                else -> repository.findAll()
            }

        return items.map { it.asDomainObject() }
    }
}
