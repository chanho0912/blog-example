package com.noah.simpleitemservice.repository.jpa

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.domain.ItemEntity
import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.ItemSearchCommand
import com.noah.simpleitemservice.repository.ItemUpdateCommand
import jakarta.persistence.EntityManager
import jakarta.persistence.TypedQuery
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils

@Repository
class JpaItemRepository(
    private val em: EntityManager
) : ItemRepository {

    override fun save(item: Item): Item {
        val entity = item.toEntity()
        em.persist(entity)
        return item.copy(id = entity.id)
    }

    override fun update(itemId: Long, updateCommand: ItemUpdateCommand) {
        em.find(
            ItemEntity::class.java, itemId
        )
            .asDomainObject()
            .copy(
                id = itemId,
                itemName = updateCommand.itemName,
                price = updateCommand.price,
                quantity = updateCommand.quantity
            ).let { save(it) }
    }

    override fun findById(id: Long): Item? {
        return em.find(
            ItemEntity::class.java, id
        )
            .asDomainObject()
    }

    override fun findAll(searchCommand: ItemSearchCommand): List<Item> {
        var jpql = """
            select i from ItemEntity i
        """.trimIndent()

        val maxPrice = searchCommand.maxPrice
        val itemName = searchCommand.itemName

        if (StringUtils.hasText(itemName) || maxPrice != null) {
            jpql += " where"
        }

        var andFlag = false
        val param = mutableListOf<Any?>()

        if (StringUtils.hasText(itemName)) {
            jpql += " i.itemName like concat('%',:itemName,'%')"
            param.add(itemName)
            andFlag = true
        }

        if (maxPrice != null) {
            if (andFlag) {
                jpql += " and"
            }
            jpql += " i.price <= :maxPrice"
            param.add(maxPrice)
        }

        val query: TypedQuery<ItemEntity> = em.createQuery(jpql, ItemEntity::class.java)
        if (StringUtils.hasText(itemName)) {
            query.setParameter("itemName", itemName)
        }
        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice)
        }

        return query.resultList?.map { it.asDomainObject() } ?: emptyList()
    }
}

internal fun ItemEntity.asDomainObject(): Item =
    Item(id = id, itemName = itemName, price = price, quantity = quantity)

internal fun Item.toEntity(): ItemEntity =
    ItemEntity(id = id, itemName = itemName, price = price, quantity = quantity)
