package com.noah.datajpa.transaction

import jakarta.persistence.EntityManager
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val repository: OrderRepository,
    private val em: EntityManager
) {
    private val logger = KotlinLogging.logger {}

    @Transactional
    fun order(order: Order) {
        logger.info { "call order" }
        repository.save(order)

        logger.info { "pay process start" }
        when (order.username) {
            "exception" -> {
                logger.info { "system exception occur" }
                throw RuntimeException("system exception")
            }
            "not-enough-money" -> {
                logger.info { "not enough money" }
                order.payStatus = "wait"
                throw NotEnoughMoneyException("not enough money")
            }
            else -> {
                logger.info { "pay success" }
                order.payStatus = "complete"
            }
        }
        logger.info { "pay process end" }
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): Order? {
        val order = repository.findByIdOrNull(id)!!
        val order2 = em.createQuery("select o from Order o where o.id = :id", Order::class.java)
            .setParameter("id", id)
            .singleResult
        return order
    }
}
