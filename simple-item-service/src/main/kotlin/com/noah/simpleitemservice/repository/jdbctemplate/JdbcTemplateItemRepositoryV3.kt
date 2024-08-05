package com.noah.simpleitemservice.repository.jdbctemplate

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.ItemSearchCommand
import com.noah.simpleitemservice.repository.ItemUpdateCommand
import org.springframework.jdbc.core.DataClassRowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.util.StringUtils
import javax.sql.DataSource

/**
 * SimpleJdbcInsert
 */
class JdbcTemplateItemRepositoryV3(
    private val dataSource: DataSource,
    private val jdbcTemplate: NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource),
) : ItemRepository {
    private val simpleJdbcInsert: SimpleJdbcInsert = SimpleJdbcInsert(dataSource).apply {
        tableName = "item"
        usingGeneratedKeyColumns("id")
    }

    override fun save(item: Item): Item {
        val key = simpleJdbcInsert.executeAndReturnKey(BeanPropertySqlParameterSource(item))
        return item.copy(id = key.toLong())
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
