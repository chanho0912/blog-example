package com.noah.springdata.jdbc.exception.translator

import com.noah.springdata.jdbc.connection.ConnectionConst
import com.noah.springdata.jdbc.domain.Member
import com.noah.springdata.jdbc.repository.exception.MyDatabaseException
import com.noah.springdata.jdbc.repository.exception.MyDuplicateKeyException
import mu.KotlinLogging
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.support.JdbcUtils
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.*
import javax.sql.DataSource


class ExTranslatorV1Test {

    lateinit var repository: Repository
    lateinit var service: Service

    @BeforeEach
    fun init() {
        val ds = DriverManagerDataSource(
            ConnectionConst.URL,
            ConnectionConst.USERNAME,
            ConnectionConst.PASSWORD
        )
        repository = Repository(ds)
        service = Service(repository)
    }

    @Test
    fun duplicateKey() {
        service.create("myId")
        service.create("myId")
    }

    class Service(
        private val repository: Repository
    ) {
        private val logger = KotlinLogging.logger {}

        fun create(memberId: String) {
            try {
                val saved = repository.save(Member(memberId, 0))
                logger.info { "saveId=$memberId" }
            } catch (e: MyDuplicateKeyException) {
                logger.info { "키 중복, 복구 시도" }
                val retryId = generateNewId(memberId)
                logger.info { "retryId=$retryId" }
                repository.save(Member(retryId, 0))
            } catch (e: MyDatabaseException) {
                logger.error { "데이터 접근 계층 예외 $e" }
                throw e
            }
        }

        fun generateNewId(memberId: String): String {
            return memberId + Random().nextInt(100000)
        }

    }

    class Repository(
        private val datasource: DataSource
    ) {

        fun save(member: Member): Member {
            val sql = """
                insert into member(member_id, money) values (?,?)
            """.trimIndent()

            var connection: Connection? = null
            var pstmt: PreparedStatement? = null

            try {
                connection = datasource.connection
                pstmt = connection.prepareStatement(sql)
                pstmt.setString(1, member.memberId)
                pstmt.setInt(2, member.money)
                pstmt.executeUpdate()
                return member
            } catch (e: SQLException) {
                if (e.sqlState.equals("23000")) throw MyDuplicateKeyException(e)
                throw MyDatabaseException(e)
            } finally {
                JdbcUtils.closeStatement(pstmt)
                JdbcUtils.closeConnection(connection)
            }
        }
    }
}
