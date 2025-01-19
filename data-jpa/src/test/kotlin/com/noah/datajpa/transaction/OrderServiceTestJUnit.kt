package com.noah.datajpa.transaction

import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.TestConstructor

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderServiceTestJUnit(private val orderRepository: OrderRepository) {
    val logger = KotlinLogging.logger {}

    @Test
    fun order() {
        val order = Order(username = "KCH")
        orderRepository.save(order)

        val actual = orderRepository.findByIdOrNull(order.id)
        logger.info { "saved: $actual"}

        val newUsername = "KCH2"
        order.username = newUsername
        orderRepository.save(order)

        val updated = orderRepository.findByIdOrNull(order.id)
        logger.info { "updated: $updated"}

        orderRepository.delete(order)
        val deleted = orderRepository.findByIdOrNull(order.id)
        logger.info { "deleted: $deleted"}
    }
}
