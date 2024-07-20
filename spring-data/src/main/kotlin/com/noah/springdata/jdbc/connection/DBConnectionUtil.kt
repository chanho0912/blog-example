package com.noah.springdata.jdbc.connection

import mu.KLogging
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DBConnectionUtil {
    companion object : KLogging() {
        fun getConnection(): Connection {
            try {
                // Library에 있는 DriverManager를 찾아서 Connection을 가져온다.
                // JDBC의 DriverManager는 라이브러리에 등록된 DB 드라이버들을 관리하고, 커넥션을 획득하는 기능을 제공한다.
                val connection = DriverManager.getConnection(
                    ConnectionConst.URL,
                    ConnectionConst.USERNAME,
                    ConnectionConst.PASSWORD
                )
                logger.info { "Connected to the database successfully. connection: $connection, class: ${connection.javaClass}" }
                return connection
            } catch (e: SQLException) {
                throw IllegalStateException(e)
            }
        }
    }
}
