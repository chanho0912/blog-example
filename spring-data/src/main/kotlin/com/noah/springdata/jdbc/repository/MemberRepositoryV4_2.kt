package com.noah.springdata.jdbc.repository

import com.noah.springdata.jdbc.domain.Member
import com.noah.springdata.jdbc.repository.exception.MyDatabaseException
import mu.KLogging
import org.springframework.jdbc.datasource.DataSourceUtils
import org.springframework.jdbc.support.JdbcUtils
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator
import org.springframework.jdbc.support.SQLExceptionTranslator
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import javax.sql.DataSource

/**
 * SQLExceptionTranslator 추가
 */
class MemberRepositoryV4_2(
    private val dataSource: DataSource,
    private val sqlExceptionTranslator: SQLExceptionTranslator = SQLErrorCodeSQLExceptionTranslator()
) : MemberRepository {
    override fun save(member: Member): Member {
        val sql = """
            insert into member (member_id, money) values (?, ?)
        """.trimIndent()
        var connection: Connection? = null
        var pstmt: PreparedStatement? = null

        try {
            connection = getConnection()
            pstmt = connection.prepareStatement(sql)
            pstmt.setString(1, member.memberId)
            pstmt.setInt(2, member.money)
            pstmt.executeUpdate()
            return member
        } catch (e: SQLException) {
            throw sqlExceptionTranslator.translate("save", sql, e)!!
        } finally {
            close(connection, pstmt, null)
        }
    }

    override fun findById(memberId: String): Member {
        val sql = """
            select * from member where member_id = ?
        """.trimIndent()

        var connection: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null

        try {
            connection = getConnection()
            pstmt = connection.prepareStatement(sql)
            pstmt.setString(1, memberId)
            rs = pstmt.executeQuery()

            return if (rs.next()) {
                Member(
                    memberId = rs.getString("member_id"),
                    money = rs.getInt("money")
                )
            } else throw NoSuchElementException("member not found with member_id=$memberId")

        } catch (e: SQLException) {
            throw sqlExceptionTranslator.translate("findById", sql, e)!!
        } finally {
            close(connection, pstmt, rs)
        }
    }

    override fun update(memberId: String, money: Int) {
        val sql = """
            update member set money=? where member_id=?
        """.trimIndent()

        var connection: Connection? = null
        var pstmt: PreparedStatement? = null

        try {
            connection = getConnection()
            pstmt = connection.prepareStatement(sql)
            pstmt.setInt(1, money)
            pstmt.setString(2, memberId)
            val resultSize = pstmt.executeUpdate()
            logger.info { "result_size: $resultSize" }
        } catch (e: SQLException) {
            throw sqlExceptionTranslator.translate("update", sql, e)!!
        } finally {
            close(connection, pstmt, null)
        }
    }

    override fun delete(memberId: String) {
        val sql = """
            delete from member where member_id=?
        """.trimIndent()

        var connection: Connection? = null
        var pstmt: PreparedStatement? = null

        try {
            connection = getConnection()
            pstmt = connection.prepareStatement(sql)
            pstmt.setString(1, memberId)
            val resultSize = pstmt.executeUpdate()
            logger.info { "result_size: $resultSize" }
        } catch (e: SQLException) {
            throw sqlExceptionTranslator.translate("delete", sql, e)!!
        } finally {
            close(connection, pstmt, null)
        }
    }

    private fun getConnection(): Connection {
        // Transaction 동기화를 사용하려면 이거 써야함
        // TransactionSynchronizationManager가 관리하는 커넥션이 있으면 해당 커넥션을 반환한다.
        // 관리하는 커넥션이 없는 경우 새로운 커넥션을 만들어서 반환한다.
        return DataSourceUtils.getConnection(dataSource)
    }

    private fun close(
        con: Connection?,
        pstmt: PreparedStatement?,
        rs: ResultSet? = null
    ) {
        JdbcUtils.closeResultSet(rs)
        JdbcUtils.closeStatement(pstmt)
        // Transaction 동기화를 사용하려면 이거 써야함
        // TransactionSynchronizationManager가 관리하는 커넥션이 있으면 해당 커넥션을 유지한다.
        // 트랜잭션 동기화 매니저가 관리하는 커넥션이 없는 경우 해당 커넥션을 닫는다.
        DataSourceUtils.releaseConnection(con, dataSource)
    }

    companion object : KLogging()
}
