package com.noah.springdata.jdbc.service

import com.noah.springdata.jdbc.repository.MemberRepositoryV3
import mu.KotlinLogging
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
class MemberServiceV3_1(
    private val repository: MemberRepositoryV3,
    private val txManager: PlatformTransactionManager
) {
    val logger = KotlinLogging.logger { }
    fun accountTransfer(fromId: String, toId: String, money: Int) {
        val status = txManager.getTransaction(DefaultTransactionDefinition())
        try {
            bizLogic(fromId, toId, money)
            txManager.commit(status)
        } catch (e: Exception) {
            txManager.rollback(status)
            throw IllegalStateException(e)
        }
        // txManager가 commit 혹은 rollback 후 커넥션 정리를 다 해줌.
    }

    private fun bizLogic(fromId: String, toId: String, money: Int) {
        val from = repository.findById(fromId)
        val to = repository.findById(toId)

        repository.update(from.memberId, from.money - money)

        check(to.memberId != "ex") { "exception while transfer" }

        repository.update(to.memberId, to.money + money)
    }
}
