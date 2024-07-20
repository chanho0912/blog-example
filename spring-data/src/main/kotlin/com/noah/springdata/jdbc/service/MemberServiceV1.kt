package com.noah.springdata.jdbc.service

import com.noah.springdata.jdbc.repository.MemberRepositoryV1

class MemberServiceV1(
    private val repository: MemberRepositoryV1
) {

    fun accountTransfer(fromId: String, toId: String, money: Int) {
        val from = repository.findById(fromId)
        val to = repository.findById(toId)

        repository.update(from.memberId, from.money - money)

        check(to.memberId != "ex") { "exception while transfer" }

        repository.update(to.memberId, to.money + money)
    }
}
