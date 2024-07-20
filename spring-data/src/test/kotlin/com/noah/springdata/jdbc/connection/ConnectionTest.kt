package com.noah.springdata.jdbc.connection

import com.zaxxer.hikari.HikariDataSource
import io.kotest.core.spec.style.FunSpec
import mu.KLogger
import mu.KotlinLogging
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.sql.DriverManager
import javax.sql.DataSource

class ConnectionTest : FunSpec({
    val logger = KotlinLogging.logger { }

    test("driverManager") {
        DriverManager.getConnection(
            ConnectionConst.URL,
            ConnectionConst.USERNAME,
            ConnectionConst.PASSWORD
        )
    }

    test("dataSourceDriverManager") {
        // DriverManager를 사용하기 때문에 항상 새로운 커넥션을 획득

        val datasource = DriverManagerDataSource(
            ConnectionConst.URL,
            ConnectionConst.USERNAME,
            ConnectionConst.PASSWORD
        )

        useDatasource(datasource, logger)
    }

    test("dataSourceConnectionPool") {
        val datasource = HikariDataSource().apply {
            jdbcUrl = ConnectionConst.URL
            username = ConnectionConst.USERNAME
            password = ConnectionConst.PASSWORD
            maximumPoolSize = 10
            poolName = "hikari-pool"
        }

        useDatasource(datasource, logger)
        Thread.sleep(5000)
    }
})

private fun useDatasource(datasource: DataSource, logger: KLogger) {
    val con1 = datasource.connection
    val con2 = datasource.connection

    logger.info { "connection=$con1, class=${con1.javaClass}" }
    logger.info { "connection=$con2, class=${con2.javaClass}" }
}
