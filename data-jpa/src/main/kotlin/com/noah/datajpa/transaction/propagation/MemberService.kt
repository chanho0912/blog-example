package com.noah.datajpa.transaction.propagation

import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val logRepository: LogRepository
) {
    private val logger = KotlinLogging.logger {}

    @Transactional
    fun joinV1(username: String) {
        val member = Member(username = username)
        memberRepository.save(member)
        logRepository.save(Log(message = username))
    }
}