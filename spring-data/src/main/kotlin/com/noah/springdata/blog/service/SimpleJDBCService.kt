package com.noah.springdata.blog.service

import com.noah.springdata.jdbc.connection.DBConnectionUtil
import mu.KotlinLogging
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class SimpleJDBCService {
    private val logger = KotlinLogging.logger {}

    fun insert(id: Int, name: String) {
        val sql = """
            insert into t1 (id, name) values (?, ?)
        """.trimIndent()
        var connection: Connection? = null
        var pstmt: PreparedStatement? = null

        try {
            connection = DBConnectionUtil.getConnection()
            pstmt = connection.prepareStatement(sql)
            pstmt.setInt(1, id)
            pstmt.setString(2, name)
            pstmt.executeUpdate()
        } catch (e: SQLException) {
            logger.error { "Error: ${e.message}" }
            throw e
        } finally {
            close(connection, pstmt, null)
        }
    }

    fun findById(id: Int): Pair<Int, String> {
        val sql = """
            select * from t1 where id = ?
        """.trimIndent()

        var connection: Connection? = null
        var pstmt: PreparedStatement? = null
        var rs: ResultSet? = null

        try {
            connection = DBConnectionUtil.getConnection()
            pstmt = connection.prepareStatement(sql)
            pstmt.setInt(1, id)
            rs = pstmt.executeQuery()

            return if (rs.next()) {
                logger.info { "id:${rs.getInt("id")} - name:${rs.getString("name")}" }
                Pair<Int, String>(
                    rs.getInt("id"),
                    rs.getString("name")
                )
            } else throw NoSuchElementException("id=$id")

        } catch (e: SQLException) {
            logger.error { "Error: ${e.message}" }
            throw e
        } finally {
            close(connection, pstmt, rs)
        }

    }

    private fun close(
        con: Connection?,
        pstmt: PreparedStatement?,
        rs: ResultSet? = null
    ) {
        if (rs != null) {
            try {
                rs.close()
            } catch (e: SQLException) {
                logger.error { "Error: ${e.message}" }
            }
        }

        if (pstmt != null) {
            try {
                pstmt.close()
            } catch (e: SQLException) {
                logger.error { "Error: ${e.message}" }
            }
        }

        if (con != null) {
            try {
                con.close()
            } catch (e: SQLException) {
                logger.error { "Error: ${e.message}" }
            }
        }
    }
}
