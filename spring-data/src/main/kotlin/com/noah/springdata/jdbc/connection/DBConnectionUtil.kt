package com.noah.springdata.jdbc.connection

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DBConnectionUtil {
    companion object {
        fun getConnection(): Connection {
            try {
                val connection = DriverManager.getConnection(
                    ConnectionConst.URL,
                    ConnectionConst.USERNAME,
                    ConnectionConst.PASSWORD
                )
                println("Connected to the database successfully. connection: $connection, class: ${connection.javaClass}")
                return connection
            } catch (e: SQLException) {
                throw IllegalStateException(e)
            }
        }
    }
}
