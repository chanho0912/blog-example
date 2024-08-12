package com.noah.simpleitemservice.repository.jpa

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.domain.QItemEntity.itemEntity
import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.ItemSearchCommand
import com.noah.simpleitemservice.repository.JpaLikePatternUtils
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils

@Repository
class JpaItemRepositoryV3(
    private val em: EntityManager,
    // QueryDsl은 JPQL을 만들어내는 빌더의 역할.
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(em)
) : ItemRepository by JpaItemRepository(em) {

    override fun findAll(searchCommand: ItemSearchCommand): List<Item> {
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

        return queryFactory.selectFrom(itemEntity)
            .where(predicate)
            .fetch()
            .map { it.asDomainObject() }
    }
}
