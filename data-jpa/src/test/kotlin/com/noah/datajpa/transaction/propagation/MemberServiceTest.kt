package com.noah.datajpa.transaction.propagation

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberServiceTest(
    private val memberService: MemberService,
    private val memberRepository: MemberRepository,
    private val logRepository: LogRepository
) : FunSpec({

    /**
     * memberService      @Transactional off
     * memberRepository   @Transactional on
     * logRepository      @Transactional on
     */
    test("outerTxOff_success") {
        val username = "outerTxOff_success"

        memberService.joinV1(username)

        val member = memberRepository.find(username)!!
        member.username shouldBe username

        val log = logRepository.find(username)!!
        log.message shouldBe username
    }

    /**
     * memberService      @Transactional off
     * memberRepository   @Transactional on
     * logRepository      @Transactional on
     *  - Exception 발생
     */
    test("outerTxOff_fail") {
        val username = "outerTxOff_error"

        assertThrows<RuntimeException> { memberService.joinV1(username) }

        val member = memberRepository.find(username)!!
        member.username shouldBe username

        val log = logRepository.find(username)
        log shouldBe null
    }

    /**
     * memberService      @Transactional on
     * memberRepository   @Transactional off
     * logRepository      @Transactional off
     */
    test("single transaction") {
        val username = "outerTxOff_success"

        memberService.joinV1(username)

        val member = memberRepository.find(username)!!
        member.username shouldBe username

        val log = logRepository.find(username)!!
        log.message shouldBe username
    }
})
