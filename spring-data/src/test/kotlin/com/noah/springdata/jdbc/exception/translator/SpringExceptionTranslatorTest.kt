package com.noah.springdata.jdbc.exception.translator

import com.noah.springdata.jdbc.connection.ConnectionConst
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.jdbc.BadSqlGrammarException
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator
import java.sql.SQLException
import javax.sql.DataSource

class SpringExceptionTranslatorTest {
    lateinit var dataSource: DataSource

    private val logger = KotlinLogging.logger {}

    @BeforeEach
    fun init() {
        dataSource = DriverManagerDataSource(
            ConnectionConst.URL,
            ConnectionConst.USERNAME,
            ConnectionConst.PASSWORD
        )
    }

    @Test
    fun badSql() {
        val sql = "select bad grammar"

        try {
            val connection = dataSource.connection
            val stmt = connection.prepareStatement(sql)
            stmt.executeQuery()
        } catch (e: SQLException) {
            assertThat(e.errorCode).isEqualTo(1054)
            e.printStackTrace()
        }
    }

    @Test
    fun exceptionTranslator() {
        val sql = "select bad grammar"

        try {
            val connection = dataSource.connection
            val pstmt = connection.prepareStatement(sql)
            pstmt.executeQuery()
        } catch (e: SQLException) {
            logger.info { e }
            val translator = SQLErrorCodeSQLExceptionTranslator(dataSource)
            val translated = translator.translate("select", sql, e)

            assertThat(translated).isInstanceOf(BadSqlGrammarException::class.java)
        }
    }
}
