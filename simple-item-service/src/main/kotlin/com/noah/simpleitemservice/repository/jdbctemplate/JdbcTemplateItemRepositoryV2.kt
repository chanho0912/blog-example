package com.noah.simpleitemservice.repository.jdbctemplate

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.ItemSearchCommand
import com.noah.simpleitemservice.repository.ItemUpdateCommand
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.util.StringUtils
import javax.sql.DataSource

/**
 * 기존 방식 -> 순서대로 파라미터를 바인딩 하는데, 실수할 여지가 큼.
 * 코드 몇줄 줄이는거보다 모호함이 없는게 가장 마음이 편함.
 *
 * NamedParameterJdbcTemplate
 * SqlParameterSource
 * - BeanPropertySqlParameterSource
 * - MapSqlParameterSource
 * Map
 *
 * BeanPropertyRowMapper
 * DataClassRowMapper << Kotlin에서 no argument constructor를 추가하지 않고, setter를 추가하지 않아도 동작
 */
class JdbcTemplateItemRepositoryV2(
    private val dataSource: DataSource,
    private val jdbcTemplate: NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)
) : ItemRepository {

    override fun save(item: Item): Item {
        val sql = """
            insert into item(item_name, price, quantity) 
            values(:itemName, :price, :quantity)
        """.trimIndent()
        val param = BeanPropertySqlParameterSource(item)
        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(sql, param, keyHolder)
        return item.copy(id = keyHolder.key!!.toLong())
    }

    override fun update(itemId: Long, updateCommand: ItemUpdateCommand) {
        val sql = """
            update item set item_name=:itemName, price=:price, quantity=:quantity where id=:id
        """.trimIndent()
        val param = MapSqlParameterSource()
            .addValue("itemName", updateCommand.itemName)
            .addValue("price", updateCommand.price)
            .addValue("quantity", updateCommand.quantity)
            .addValue("id", itemId)

        jdbcTemplate.update(sql, param)
    }

    override fun findById(id: Long): Item? {
        val sql = """
            select id, item_name, price, quantity from item where id=:id
        """.trimIndent()
        val param = mapOf("id" to id)

        return jdbcTemplate.queryForObject(sql, param, itemMapper())
    }

    private fun itemMapper() = DataClassRowMapper.newInstance(Item::class.java)

    override fun findAll(searchCommand: ItemSearchCommand): List<Item> {
        val itemName = searchCommand.itemName
        val maxPrice = searchCommand.maxPrice
        val param = BeanPropertySqlParameterSource(searchCommand)

        var sql = "select id, item_name, price, quantity from item"

        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql += " where"
        }

        var andFlag = false

        if (StringUtils.hasText(itemName)) {
            sql += " item_name like concat('%',:itemName,'%')"
            andFlag = true
        }

        if (maxPrice != null) {
            if (andFlag) {
                sql += " and"
            }
            sql += " price <= :maxPrice"
        }

        return jdbcTemplate.query(sql, param, itemMapper())
    }
}
