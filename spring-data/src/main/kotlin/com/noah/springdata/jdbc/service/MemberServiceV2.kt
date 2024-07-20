package com.noah.springdata.jdbc.service

import com.noah.springdata.jdbc.repository.MemberRepositoryV2
import mu.KotlinLogging
import java.sql.Connection
import javax.sql.DataSource

/**
 * 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 */
class MemberServiceV2(
    private val repository: MemberRepositoryV2,
    private val dataSource: DataSource
) {
    val logger = KotlinLogging.logger { }
    fun accountTransfer(fromId: String, toId: String, money: Int) {
        val connection = dataSource.connection
        try {
            connection.autoCommit = false

            bizLogic(connection, fromId, toId, money)

            connection.commit()
        } catch (e: Exception) {
            connection.rollback()
            throw IllegalStateException(e)
        } finally {
            if (connection != null) {
                try {
                    connection.autoCommit = true
                    connection.close()
                } catch (e: Exception) {
                    logger.error { "error: $e" }
                }
            }
        }
    }

    private fun bizLogic(
        connection: Connection,
        fromId: String,
        toId: String,
        money: Int
    ) {
        val from = repository.findById(connection, fromId)
        val to = repository.findById(connection, toId)

        repository.update(connection, from.memberId, from.money - money)

        check(to.memberId != "ex") { "exception while transfer" }

        repository.update(connection, to.memberId, to.money + money)
    }
}
