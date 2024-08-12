package com.noah.simpleitemservice.repository.v2

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.domain.QItemEntity.itemEntity
import com.noah.simpleitemservice.repository.ItemSearchCommand
import com.noah.simpleitemservice.repository.JpaLikePatternUtils
import com.noah.simpleitemservice.repository.jpa.asDomainObject
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.util.StringUtils

class ItemQueryRepositoryV2(
    private val em: EntityManager,
    private val query: JPAQueryFactory = JPAQueryFactory(em)
) {
    fun findAll(searchCommand: ItemSearchCommand): List<Item> {
        val itemName = searchCommand.itemName
        val maxPrice = searchCommand.maxPrice
        val predicate = BooleanBuilder()

        if (StringUtils.hasText(itemName)) {
            val itemNameLike = JpaLikePatternUtils.fullMatch(itemName!!)
            predicate.and(itemEntity.itemName.like(itemNameLike))
        }

        if (maxPrice != null) {
            predicate.and(itemEntity.price.loe(maxPrice))
        }

        return query.selectFrom(itemEntity)
            .where(predicate)
            .fetch()
            .map { it.asDomainObject() }
    }
}
