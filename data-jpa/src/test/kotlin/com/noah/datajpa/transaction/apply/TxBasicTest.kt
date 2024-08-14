package com.noah.datajpa.transaction.apply

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mu.KotlinLogging
import org.springframework.aop.support.AopUtils
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

@SpringBootTest
class TxBasicTest(
    private val basicService: BasicService
) : FunSpec({
    val logger = KotlinLogging.logger {}

    test("proxy check") {
        logger.info { "aop class=${basicService.javaClass.simpleName}" }
        AopUtils.isAopProxy(basicService) shouldBe true
    }

    test("transaction test") {
        with(basicService) {
            tx()
            nonTx()
        }
    }
}) {
    @Component
    class BasicService {
        private val logger = KotlinLogging.logger {}

        @Transactional
        fun tx() {
            logger.info { "call transaction" }
            val isTransactionActive = TransactionSynchronizationManager.isActualTransactionActive()
            logger.info { "transaction active = $isTransactionActive" }
        }

        fun nonTx() {
            logger.info { "call transaction" }
            val isTransactionActive = TransactionSynchronizationManager.isActualTransactionActive()
            logger.info { "transaction active = $isTransactionActive" }
        }
    }
}

