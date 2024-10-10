package com.noah.jpashop.service.validator

import com.noah.jpashop.exception.DuplicateMemberException
import com.noah.jpashop.repository.MemberRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class MemberServiceValidator(
    private val memberRepository: MemberRepository
) {
    private val logger = KotlinLogging.logger {}

    fun validateDuplicateMember(name: String) {
        val findMembers = memberRepository.findByName(name)
        if (findMembers.isNotEmpty()) {
            logger.error { "이미 존재하는 회원입니다." }
            throw DuplicateMemberException(message = "이미 존재하는 회원입니다.")
        }
    }
}