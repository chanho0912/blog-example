package com.noah.springdata.jdbc.service

import com.noah.springdata.jdbc.connection.ConnectionConst
import com.noah.springdata.jdbc.domain.Member
import com.noah.springdata.jdbc.repository.MemberRepositoryV1
import com.noah.springdata.jdbc.repository.MemberRepositoryV2
import com.noah.springdata.jdbc.repository.MemberRepositoryV3
import com.zaxxer.hikari.HikariDataSource
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import org.springframework.jdbc.datasource.DataSourceTransactionManager

class MemberServiceV3_2Test : FunSpec({
    val datasource = HikariDataSource().apply {
        jdbcUrl = ConnectionConst.URL
        username = ConnectionConst.USERNAME
        password = ConnectionConst.PASSWORD
    }
    val txManager = DataSourceTransactionManager(datasource)
    val repository = MemberRepositoryV3(datasource)
    val service = MemberServiceV3_2(repository, txManager)

    val member_A = "memberA"
    val member_B = "memberB"
    val member_EX = "ex"

    test("normal transfer") {
        repository.delete(member_A)
        repository.delete(member_B)

        val memberA = Member(memberId = member_A, money = 10000)
        val memberB = Member(memberId = member_B, money = 10000)

        repository.save(memberA)
        repository.save(memberB)

        service.accountTransfer(member_A, member_B, 2000)

        val savedMemberA = repository.findById(member_A)
        val savedMemberB = repository.findById(member_B)

        savedMemberA.money shouldBe 8000
        savedMemberB.money shouldBe 12000
    }

    test("exception while transfer") {
        repository.delete(member_A)
        repository.delete(member_EX)

        val memberA = Member(memberId = member_A, money = 10000)
        val memberEX = Member(memberId = member_EX, money = 10000)

        repository.save(memberA)
        repository.save(memberEX)

        assertThrows<IllegalStateException> {
            service.accountTransfer(member_A, member_EX, 2000)
        }

        val savedMemberA = repository.findById(member_A)
        val savedMemberB = repository.findById(member_EX)

        savedMemberA.money shouldBe 10000
        savedMemberB.money shouldBe 10000
    }
})
