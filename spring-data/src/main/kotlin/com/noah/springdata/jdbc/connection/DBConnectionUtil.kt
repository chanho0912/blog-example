package com.noah.springdata.jdbc.connection

import mu.KLogging
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/**
 * Connection을 획득하는 과정
 * 1. 애플리케이션 로직은 DB 드라이버를 통해 커넥션을 조회한다.
 * 2. DB 드라이버는 DB와 TCP/IP 커넥션을 연결한다. 물론 이 과정에서 3 way handshake와 같은 TCP/IP 연결을 위한 네트워크 동작이 발생한다.
 * 3. DB 드라이버는 TCP/IP 커넥션이 연결되면 ID, PW와 기타 부가정보를 DB에 전달하다.
 * 4. DB는 ID.PW를 통해 내부 인증을 완료하고, 내부에 DB 세션을 생성한다.
 * 5. DB는 커넥션 생성이 완료되었다는 응답을 보낸다.
 * 6. DB 드라이버는 커넥션 객체를 생성해서 클라이언트에 반환한다.
 *
 * 즉 엄청 고비용 작업임.
 */
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
