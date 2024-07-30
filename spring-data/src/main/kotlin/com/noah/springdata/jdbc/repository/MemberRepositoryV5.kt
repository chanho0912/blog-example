package com.noah.springdata.jdbc.repository

import com.noah.springdata.jdbc.domain.Member
import mu.KLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import javax.sql.DataSource

/**
 * JdbcTemplate 사용
 */
class MemberRepositoryV5(
    private val dataSource: DataSource,
    private val jdbcTemplate: JdbcTemplate = JdbcTemplate(dataSource)
) : MemberRepository {
    override fun save(member: Member): Member {
        val sql = """
            insert into member (member_id, money) values (?, ?)
        """.trimIndent()

        jdbcTemplate.update(sql, member.memberId, member.money)
        return member
    }

    private fun memberRowMapper(): RowMapper<Member> {
        return RowMapper { rs, _ ->
            Member(
                memberId = rs.getString("member_id"),
                money = rs.getInt("money")
            )
        }
    }

    override fun findById(memberId: String): Member {
        val sql = """
            select * from member where member_id = ?
        """.trimIndent()

        return jdbcTemplate.queryForObject(sql, memberRowMapper(), memberId)
            ?: run {
                logger.error { "member not found with member_id=$memberId" }
                throw NoSuchElementException("member not found with member_id=$memberId")
            }
    }

    override fun update(memberId: String, money: Int) {
        val sql = """
            update member set money=? where member_id=?
        """.trimIndent()

        jdbcTemplate.update { con ->
            val pstmt = con.prepareStatement(sql)
            pstmt.apply {
                setInt(1, money)
                setString(2, memberId)
            }
        }
    }

    override fun delete(memberId: String) {
        val sql = """
            delete from member where member_id=?
        """.trimIndent()

        jdbcTemplate.update(sql, memberId)
    }

    companion object : KLogging()
}
