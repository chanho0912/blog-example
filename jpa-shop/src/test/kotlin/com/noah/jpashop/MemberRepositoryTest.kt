package com.noah.jpashop

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberRepositoryTest(
    private val memberRepository: MemberRepository
) : FunSpec({

    test("member save") {
        // given
        val member = Member(username = "memberA")

        // when
        val savedId = memberRepository.save(member)

        // then
        val findMember = memberRepository.findById(savedId)
        findMember.id shouldBe member.id
        findMember.username shouldBe member.username
        // 같은 영속성 컨텍스트에서 조회되었다면 true
//        findMember shouldBe member
    }

})
