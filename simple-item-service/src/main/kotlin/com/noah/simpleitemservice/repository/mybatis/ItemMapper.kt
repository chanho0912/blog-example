package com.noah.simpleitemservice.repository.mybatis

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.repository.ItemSearchCommand
import com.noah.simpleitemservice.repository.ItemUpdateCommand
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface ItemMapper {
    fun save(item: Item)

    fun update(@Param("id") id: Long, @Param("updateCommand") itemUpdateCommand: ItemUpdateCommand)

    fun findAll(searchCommand: ItemSearchCommand): List<Item>

    fun findById(id: Long): Item?
}
