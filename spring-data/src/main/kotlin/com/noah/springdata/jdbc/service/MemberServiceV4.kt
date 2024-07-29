package com.noah.springdata.jdbc.service

import com.noah.springdata.jdbc.repository.MemberRepository
import com.noah.springdata.jdbc.repository.MemberRepositoryV3
import mu.KotlinLogging
import org.springframework.transaction.annotation.Transactional

/**
 * 예외 누수 문제 해결
 * SQLException 제거
 *
 * MemberRepository 인터페이스 의존
 */
open class MemberServiceV4(
    private val repository: MemberRepository
) {
    val logger = KotlinLogging.logger { }

    @Transactional
    open fun accountTransfer(fromId: String, toId: String, money: Int) {
        bizLogic(fromId, toId, money)
    }

    private fun bizLogic(fromId: String, toId: String, money: Int) {
        val from = repository.findById(fromId)
        val to = repository.findById(toId)

        repository.update(from.memberId, from.money - money)

        check(to.memberId != "ex") { "exception while transfer" }

        repository.update(to.memberId, to.money + money)
    }
}
