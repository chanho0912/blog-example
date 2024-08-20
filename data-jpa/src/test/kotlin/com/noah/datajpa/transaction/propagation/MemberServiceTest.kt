package com.noah.datajpa.transaction.propagation

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.UnexpectedRollbackException

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
        val username = "single transaction"

        memberService.joinV1(username)

        val member = memberRepository.find(username)!!
        member.username shouldBe username

        val log = logRepository.find(username)!!
        log.message shouldBe username
    }

    /**
     * memberService      @Transactional on
     * memberRepository   @Transactional on
     * logRepository      @Transactional on
     */
    test("outerTxOn_success") {
        val username = "outerTxOn_success"

        memberService.joinV1(username)

        val member = memberRepository.find(username)!!
        member.username shouldBe username

        val log = logRepository.find(username)!!
        log.message shouldBe username
    }

    /**
     * memberService      @Transactional on
     * memberRepository   @Transactional on
     * logRepository      @Transactional on
     *  - Exception 발생
     */
    test("outerTxOn_fail") {
        val username = "outerTxOn_error"

        assertThrows<RuntimeException> { memberService.joinV1(username) }

        val member = memberRepository.find(username)
        member shouldBe null

        val log = logRepository.find(username)
        log shouldBe null
    }

    /**
     * memberService      @Transactional on
     * memberRepository   @Transactional on
     * logRepository      @Transactional on
     *  - Exception 발생
     */
    xtest("recoverError_fail") {
        val username = "recover_error_fail"

        assertThrows<UnexpectedRollbackException> { memberService.joinV2(username) }

        val member = memberRepository.find(username)
        member shouldBe null

        val log = logRepository.find(username)
        log shouldBe null
    }

    /**
     * memberService      @Transactional on
     * memberRepository   @Transactional on
     * logRepository      @Transactional on(Requires New)
     *  - Exception 발생
     */
    test("recover_error_success") {
        val username = "recover_error_success"
        memberService.joinV2(username)

        val member = memberRepository.find(username)!!
        member.username shouldBe username

        val log = logRepository.find(username)
        log shouldBe null
    }
})
