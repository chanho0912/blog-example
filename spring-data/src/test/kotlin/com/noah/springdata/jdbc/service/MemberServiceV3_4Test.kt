package com.noah.springdata.jdbc.service

import com.noah.springdata.jdbc.domain.Member
import com.noah.springdata.jdbc.repository.MemberRepositoryV3
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import javax.sql.DataSource

@TestConfiguration
class TestConfig2 {
    @Bean
    fun memberRepositoryV3(dataSource: DataSource): MemberRepositoryV3 = MemberRepositoryV3(dataSource)

    @Bean
    fun memberServiceV3_3(repository: MemberRepositoryV3): MemberServiceV3_3 = MemberServiceV3_3(repository)
}

@SpringBootTest
@Import(TestConfig2::class)
class MemberServiceV3_4Test(
    private val memberRepositoryV3: MemberRepositoryV3,
    private val memberServiceV3_3: MemberServiceV3_3
) : FunSpec({

    val member_A = "memberA"
    val member_B = "memberB"
    val member_EX = "ex"

    test("normal transfer") {
        memberRepositoryV3.delete(member_A)
        memberRepositoryV3.delete(member_B)

        val memberA = Member(memberId = member_A, money = 10000)
        val memberB = Member(memberId = member_B, money = 10000)

        memberRepositoryV3.save(memberA)
        memberRepositoryV3.save(memberB)

        memberServiceV3_3.accountTransfer(member_A, member_B, 2000)

        val savedMemberA = memberRepositoryV3.findById(member_A)
        val savedMemberB = memberRepositoryV3.findById(member_B)

        savedMemberA.money shouldBe 8000
        savedMemberB.money shouldBe 12000
    }

    test("exception while transfer") {
        memberRepositoryV3.delete(member_A)
        memberRepositoryV3.delete(member_EX)

        val memberA = Member(memberId = member_A, money = 10000)
        val memberEX = Member(memberId = member_EX, money = 10000)

        memberRepositoryV3.save(memberA)
        memberRepositoryV3.save(memberEX)

        assertThrows<IllegalStateException> {
            memberServiceV3_3.accountTransfer(member_A, member_EX, 2000)
        }

        val savedMemberA = memberRepositoryV3.findById(member_A)
        val savedMemberB = memberRepositoryV3.findById(member_EX)

        savedMemberA.money shouldBe 10000
        savedMemberB.money shouldBe 10000
    }
})
