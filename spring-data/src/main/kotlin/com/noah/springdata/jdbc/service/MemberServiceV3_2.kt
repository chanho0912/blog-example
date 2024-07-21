package com.noah.springdata.jdbc.service

import com.noah.springdata.jdbc.repository.MemberRepositoryV3
import mu.KotlinLogging
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
import org.springframework.transaction.support.TransactionTemplate

/**
 * 트랜잭션 - 트랜잭션 템플릿
 */
class MemberServiceV3_2(
    private val repository: MemberRepositoryV3,
    private val txManager: PlatformTransactionManager
) {
    private val transactionTemplate = TransactionTemplate(txManager)
    val logger = KotlinLogging.logger { }

    fun accountTransfer(fromId: String, toId: String, money: Int) {
        transactionTemplate.executeWithoutResult { _ ->
            bizLogic(fromId, toId, money)
        }
    }

    private fun bizLogic(fromId: String, toId: String, money: Int) {
        val from = repository.findById(fromId)
        val to = repository.findById(toId)

        repository.update(from.memberId, from.money - money)

        check(to.memberId != "ex") { "exception while transfer" }

        repository.update(to.memberId, to.money + money)
    }
}
