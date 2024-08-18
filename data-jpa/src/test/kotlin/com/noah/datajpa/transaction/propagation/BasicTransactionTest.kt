package com.noah.datajpa.transaction.propagation

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import mu.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.UnexpectedRollbackException
import org.springframework.transaction.interceptor.DefaultTransactionAttribute
import javax.sql.DataSource

/**
 * 모든 논리 트랜잭션이 커밋되어야 물리적인 (실제 Database Transaction)이 커밋된다.
 * 하나의 논리 트랜잭션이라도 롤백되면 물리 트랜잭션은 롤백된다.
 */
@SpringBootTest
class BasicTransactionTest(
    private val transactionManager: PlatformTransactionManager
) : FunSpec({
    val logger = KotlinLogging.logger {}

    test("transaction commit") {
        logger.info { "transaction start" }
        val status = transactionManager.getTransaction(DefaultTransactionAttribute())

        logger.info { "transaction commit start" }
        transactionManager.commit(status)
        logger.info { "transaction commit end" }
    }

    test("transaction rollback") {
        logger.info { "transaction start" }
        val status = transactionManager.getTransaction(DefaultTransactionAttribute())

        logger.info { "transaction rollback start" }
        transactionManager.rollback(status)
        logger.info { "transaction rollback end" }
    }

    test("transaction double commit") {
        logger.info { "transaction1 start" }
        val status1 = transactionManager.getTransaction(DefaultTransactionAttribute())

        logger.info { "transaction1 commit start" }
        transactionManager.commit(status1)
        logger.info { "transaction1 commit end" }

        logger.info { "transaction2 start" }
        val status2 = transactionManager.getTransaction(DefaultTransactionAttribute())

        logger.info { "transaction2 commit start" }
        transactionManager.commit(status2)
        logger.info { "transaction2 commit end" }
    }

    test("inner transaction commit") {
        logger.info { "outer transaction start" }
        val outer = transactionManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "outer isNewTransaction()=${outer.isNewTransaction}" }

        logger.info { "inner transaction start" }
        // Participating in existing transaction
        val inner = transactionManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "inner isNewTransaction()=${inner.isNewTransaction}" }

        logger.info { "inner transaction commit" }
        transactionManager.commit(inner)

        logger.info { "outer transaction commit" }
        transactionManager.commit(outer)
    }

    test("external transaction rollback") {
        logger.info { "outer transaction start" }
        val outer = transactionManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "outer isNewTransaction()=${outer.isNewTransaction}" }

        logger.info { "inner transaction start" }
        // Participating in existing transaction
        val inner = transactionManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "inner isNewTransaction()=${inner.isNewTransaction}" }

        logger.info { "inner transaction commit" }
        transactionManager.commit(inner)

        logger.info { "outer transaction rollback" }
        transactionManager.rollback(outer)
    }

    test("internal transaction rollback") {
        logger.info { "outer transaction start" }
        val outer = transactionManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "outer isNewTransaction()=${outer.isNewTransaction}" }

        logger.info { "inner transaction start" }
        // Participating in existing transaction
        val inner = transactionManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "inner isNewTransaction()=${inner.isNewTransaction}" }

        logger.info { "inner transaction rollback" }
        // 실제 롤백은 할 수 없으므로 rollback-only 마크를 한다.
        transactionManager.rollback(inner)

        logger.info { "outer transaction commit" }
        // rollback-only 마크가 있으면 롤백된다.
        shouldThrow<UnexpectedRollbackException> {
            transactionManager.commit(outer)
        }
    }

    test("requires new inner transaction rollback") {
        logger.info { "outer transaction start" }
        val outer = transactionManager.getTransaction(DefaultTransactionAttribute())
        logger.info { "outer isNewTransaction()=${outer.isNewTransaction}" }

        logger.info { "requires new inner transaction start" }

        val inner = transactionManager.getTransaction(DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRES_NEW))
        logger.info { "inner isNewTransaction()=${inner.isNewTransaction}" }

        logger.info { "inner transaction rollback" }
        transactionManager.rollback(inner)

        logger.info { "outer transaction commit" }
        transactionManager.commit(outer)
    }
}) {

    @TestConfiguration
    class Config {
        @Bean
        fun transactionManager(dataSource: DataSource): PlatformTransactionManager {
            return DataSourceTransactionManager(dataSource)
        }
    }
}