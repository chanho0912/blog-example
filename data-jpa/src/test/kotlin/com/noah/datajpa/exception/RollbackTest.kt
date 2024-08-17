package com.noah.datajpa.exception

import io.kotest.core.spec.style.FunSpec
import mu.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class RollbackTest(
    private val service: RollbackService
) : FunSpec({

    val logger = KotlinLogging.logger {}
    test("runtimeException") {
        try {
            service.runtimeException()
        } catch (e: Exception) {
            logger.info { e.message }
        }
    }

    test("checkedException") {
        try {
            service.checkedException()
        } catch (e: Exception) {
            logger.info { e.message }
        }
    }

    test("rollbackFor") {
        try {
            service.rollbackFor()
        } catch (e: Exception) {
            logger.info { e.message }
        }
    }

}) {

    @Service
    class RollbackService {

        class MyException : Exception()

        private val logger = KotlinLogging.logger {}

        // runtime 예외 발생: 롤백
        @Transactional
        fun runtimeException() {
            logger.info { "call runtimeException" }
            throw RuntimeException("Rollback")
        }

        // 체크 예외 발생: 커밋
        @Transactional
        fun checkedException() {
            logger.info { "call checkedException" }
            throw MyException()
        }

        // 롤백 대상 예외 설정
        @Transactional(rollbackFor = [MyException::class])
        fun rollbackFor() {
            logger.info { "call rollbackFor" }
            throw MyException()
        }
    }
}