package com.noah.springdata.jdbc.service

import com.noah.springdata.jdbc.domain.Member
import com.noah.springdata.jdbc.repository.MemberRepository
import com.noah.springdata.jdbc.repository.MemberRepositoryV4_1
import com.noah.springdata.jdbc.repository.MemberRepositoryV4_2
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import javax.sql.DataSource

@TestConfiguration
class TestConfig3 {
    @Bean
    fun memberRepositoryV3(dataSource: DataSource): MemberRepository =
//        MemberRepositoryV4_1(dataSource)
        MemberRepositoryV4_2(dataSource)

    @Bean
    fun memberServiceV3_3(repository: MemberRepository): MemberServiceV4 = MemberServiceV4(repository)
}

@SpringBootTest
@Import(TestConfig3::class)
class MemberServiceV4Test(
    private val memberRepository: MemberRepository,
    private val memberServiceV4: MemberServiceV4
) : FunSpec({

    val member_A = "memberA"
    val member_B = "memberB"
    val member_EX = "ex"

    test("normal transfer") {
        memberRepository.delete(member_A)
        memberRepository.delete(member_B)

        val memberA = Member(memberId = member_A, money = 10000)
        val memberB = Member(memberId = member_B, money = 10000)

        memberRepository.save(memberA)
        memberRepository.save(memberB)

        memberServiceV4.accountTransfer(member_A, member_B, 2000)

        val savedMemberA = memberRepository.findById(member_A)
        val savedMemberB = memberRepository.findById(member_B)

        savedMemberA.money shouldBe 8000
        savedMemberB.money shouldBe 12000
    }

    test("exception while transfer") {
        memberRepository.delete(member_A)
        memberRepository.delete(member_EX)

        val memberA = Member(memberId = member_A, money = 10000)
        val memberEX = Member(memberId = member_EX, money = 10000)

        memberRepository.save(memberA)
        memberRepository.save(memberEX)

        assertThrows<IllegalStateException> {
            memberServiceV4.accountTransfer(member_A, member_EX, 2000)
        }

        val savedMemberA = memberRepository.findById(member_A)
        val savedMemberB = memberRepository.findById(member_EX)

        savedMemberA.money shouldBe 10000
        savedMemberB.money shouldBe 10000
    }
})
