package com.noah.simpleitemservice.repository.jpa

import com.noah.simpleitemservice.domain.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SpringDataJpaItemRepository : JpaRepository<ItemEntity, Long> {
    fun findByItemNameLike(itemName: String): List<ItemEntity>

    fun findByPriceLessThanEqual(price: Int): List<ItemEntity>

    // 너무 길어지면 좋지 않음.
    fun findByItemNameLikeAndPriceLessThanEqual(itemName: String, price: Int): List<ItemEntity>

    @Query("select i from ItemEntity i where i.itemName like :itemName and i.price <= :price")
    fun findItems(@Param("itemName") itemName: String, @Param("price") price: Int): List<ItemEntity>
}
