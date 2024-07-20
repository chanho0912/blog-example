package com.noah.springdata.jdbc.repository

import com.noah.springdata.jdbc.connection.ConnectionConst
import com.noah.springdata.jdbc.domain.Member
import com.zaxxer.hikari.HikariDataSource
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import mu.KotlinLogging
import org.junit.jupiter.api.assertThrows
import org.springframework.jdbc.datasource.DriverManagerDataSource

class MemberRepositoryV1Test : ShouldSpec({

    val logger = KotlinLogging.logger {}
    var memberRepository: MemberRepositoryV1

    context("MemberRepositoryTest") {
//        val datasource = DriverManagerDataSource(
//            ConnectionConst.URL,
//            ConnectionConst.USERNAME,
//            ConnectionConst.PASSWORD
//        )
        val datasource = HikariDataSource().apply {
            jdbcUrl = ConnectionConst.URL
            username = ConnectionConst.USERNAME
            password = ConnectionConst.PASSWORD
        }

        memberRepository = MemberRepositoryV1(datasource)

        should("save") {
            // given
            val member = Member("noah", 1000)

            // when
            val savedMember = memberRepository.save(member)

            // then
            savedMember shouldBe member
        }

        should("update") {
            val memberId = "noah"

            val member = memberRepository.findById(memberId)
            memberRepository.update(memberId, 20000)

            val updatedMember = memberRepository.findById(memberId)
            updatedMember.money shouldBe 20000
        }

        should("findById") {
            // given
            val memberId = "noah"

            // when
            val member = memberRepository.findById(memberId)
            logger.info { "member=$member" }

            // then
            member.memberId shouldBe memberId
        }

        should("delete") {
            val memberId = "noah"

            memberRepository.delete(memberId)
            assertThrows<NoSuchElementException> {
                memberRepository.findById(memberId)
            }
        }
    }
})
