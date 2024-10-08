package com.noah.jpashop.service

import com.noah.jpashop.domain.Member
import com.noah.jpashop.repository.MemberRepository
import com.noah.jpashop.service.validator.MemberServiceValidator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberServiceValidator: MemberServiceValidator
) {
    fun join(member: Member): Long {
        // 회원 가입
        memberServiceValidator.validateDuplicateMember(member.id)
        memberRepository.save(member)
        return member.id
    }

    fun findMembers(): List<Member> {
        // 회원 전체 조회
        return memberRepository.findAll()
    }

    fun findOne(id: Long): Member {
        // 회원 한 건 조회
        return memberRepository.findOne(id)
    }
}