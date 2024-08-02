package com.noah.simpleitemservice.repository.jdbctemplate

import com.noah.simpleitemservice.domain.Item
import com.noah.simpleitemservice.repository.ItemRepository
import com.noah.simpleitemservice.repository.ItemSearchCommand
import com.noah.simpleitemservice.repository.ItemUpdateCommand
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.util.StringUtils
import java.util.ArrayList
import java.util.Arrays
import javax.sql.DataSource

/**
 * JdbcTemplate
 */
class JdbcTemplateItemRepositoryV1(
    private val dataSource: DataSource,
    private val jdbcTemplate: JdbcTemplate = JdbcTemplate(dataSource)
) : ItemRepository {

    override fun save(item: Item): Item {
        val sql = """
            insert into item(item_name, price, quantity) values(?,?,?)
        """.trimIndent()
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            { con ->
                val prepareStatement = con.prepareStatement(sql, arrayOf("id"))
                prepareStatement.apply {
                    setString(1, item.itemName)
                    setInt(2, item.price)
                    setInt(3, item.quantity)
                }
            }, keyHolder
        )

        return item.copy(id = keyHolder.key!!.toLong())
    }

    override fun update(itemId: Long, updateCommand: ItemUpdateCommand) {
        val sql = """
            update item set item_name=?, price=?, quantity=? where id=?
        """.trimIndent()

        with(updateCommand) {
            jdbcTemplate.update(sql, itemName, price, quantity, itemId)
        }
    }

    override fun findById(id: Long): Item? {
        val sql = """
            select id, item_name, price, quantity from item where id=?
        """.trimIndent()

        return jdbcTemplate.queryForObject(sql, itemMapper(), id)
    }

    private fun itemMapper() = RowMapper { rs, _ ->
        Item(
            id = rs.getLong("id"),
            itemName = rs.getString("item_name"),
            price = rs.getInt("price"),
            quantity = rs.getInt("quantity")
        )
    }

    override fun findAll(searchCommand: ItemSearchCommand): List<Item> {
        val itemName = searchCommand.itemName
        val maxPrice = searchCommand.maxPrice

        var sql = "select id, item_name, price, quantity from item"

        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql += " where"
        }

        var andFlag = false
        val param = mutableListOf<Any?>()

        if (StringUtils.hasText(itemName)) {
            sql += " item_name like concat('%',?,'%')"
            andFlag = true
            param.add(itemName)
        }

        if (maxPrice != null) {
            if (andFlag) {
                sql += " and"
            }
            sql += " price <= ?"
            param.add(maxPrice)
        }

        return jdbcTemplate.query(sql, itemMapper(), *param.toTypedArray())
    }
}
