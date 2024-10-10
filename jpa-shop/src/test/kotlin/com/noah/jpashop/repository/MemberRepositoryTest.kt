package com.noah.jpashop.repository

import com.noah.jpashop.domain.Address
import com.noah.jpashop.domain.Member
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.support.TransactionTemplate

@SpringBootTest
class MemberRepositoryTest(
    private val memberRepository: MemberRepository,
    private val transactionTemplate: TransactionTemplate
) : FunSpec({

    test("member save") {
        // given
        val member = Member(
            name = "noah",
            address = Address(
                city = "seoul",
                street = "1",
                zipcode = "123-123"
            )
        )

        transactionTemplate.executeWithoutResult {
            // when
            memberRepository.save(member)

            // then
            val findMember = memberRepository.findOne(member.id)
            // 같은 영속성 컨텍스트에서 조회되었다면 true
            findMember shouldBe member
        }
    }

})
