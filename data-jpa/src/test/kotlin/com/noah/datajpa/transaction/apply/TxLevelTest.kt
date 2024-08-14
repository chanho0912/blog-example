package com.noah.datajpa.transaction.apply

import io.kotest.core.spec.style.FunSpec
import mu.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

/**
 * Spring의 우선순위는 항상 더 구체적이고 자세한 것이 높은 우선순위를 가진다
 */
@SpringBootTest
class TxLevelTest(
    private val service: LevelService
) : FunSpec({
    val logger = KotlinLogging.logger {}

    test("order test") {
        with(service) {
            write()
            read()
        }
    }
}) {
    // interface에 @Transactional을 붙여도 동작은 하지만 권장하지 않음 (Proxy 생성 기술에 따라 적용되지 않는 경우가 있음)
    // Spring Aop는 public 메서드만 적용 가능.
    // PostConstruct와 같은 Spring 초기화 시점에는 적용이 안될수도 있다. 초기화 시점이 트랜잭션 AOP 적용 시점보다 빠르기 때문
    @Component
    /**
     * JdbcTemplate은 읽기 전용 트랜잭션 안에서 변경 기능을 실행하면 예외를 더닞ㄴ다.
     * JPA는 읽기 전용 트랜잭션의 경우 커밋 시점에 플러시를 호출하지 않는다. 또한 변경 감지를 위한 Snapshot을 생성하지 않는다.
     */
    @Transactional(readOnly = true)
    class LevelService {
        private val logger = KotlinLogging.logger {}

        @Transactional(readOnly = false)
        fun write() {
            logger.info { "call write" }
            printTxInfo()
        }

        fun read() {
            logger.info { "call read" }
            printTxInfo()
        }

        fun printTxInfo() {
            logger.info { "transaction active = ${TransactionSynchronizationManager.isActualTransactionActive()}" }
            logger.info { "is transaction readOnly = ${TransactionSynchronizationManager.isCurrentTransactionReadOnly()}" }
        }
    }
}

