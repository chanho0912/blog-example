package com.noah.jpashop.service

import com.noah.jpashop.domain.Address
import com.noah.jpashop.domain.Member
import com.noah.jpashop.exception.DuplicateMemberException
import com.noah.jpashop.repository.MemberRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.support.TransactionTemplate

@SpringBootTest
class MemberServiceTest(
    private val memberService: MemberService,
    private val transactionTemplate: TransactionTemplate,
    private val memberRepository: MemberRepository
) : FunSpec({

    test("join") {
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
            val saveId = memberService.join(member)

            // then
            memberRepository.findOne(saveId) shouldBe member
        }
    }

    test("findMembers") {
        // given
        val member1 = Member(
            name = "noah1",
            address = Address(
                city = "seoul",
                street = "1",
                zipcode = "123-123"
            )
        )
        val member2 = Member(
            name = "noah2",
            address = Address(
                city = "seoul",
                street = "1",
                zipcode = "123-123"
            )
        )

        transactionTemplate.executeWithoutResult {
            // when
            memberService.join(member1)
            memberService.join(member2)

            // then
            memberService.findMembers() shouldContainAll listOf(member1, member2)
        }
    }

    test("validate fail") {
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
            memberService.join(member)
        }

        // then
        val e = shouldThrow<DuplicateMemberException> {
            memberService.join(member)
        }
        e.message shouldBe "이미 존재하는 회원입니다."
    }

    test("findOne") {
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
            val saveId = memberService.join(member)

            // then
            memberService.findOne(saveId) shouldBe member
        }
    }
})
