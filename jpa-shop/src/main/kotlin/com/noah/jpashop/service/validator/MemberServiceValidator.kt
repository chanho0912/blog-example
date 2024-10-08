package com.noah.jpashop.service.validator

import com.noah.jpashop.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberServiceValidator(
    private val memberRepository: MemberRepository
) {

    fun validateDuplicateMember(memberId: Long) {
        val findMembers = memberRepository.findByName(memberId.toString())
        if (findMembers.isNotEmpty()) {
            throw IllegalStateException("이미 존재하는 회원입니다.")
        }
    }
}