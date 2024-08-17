package com.noah.datajpa.transaction

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class OrderServiceTest(
    private val orderService: OrderService,
    private val orderRepository: OrderRepository
) : FunSpec({

    test("complete") {
        val order = Order(username = "noah")
        orderService.order(order)

        val findOrder = orderRepository.findByIdOrNull(order.id)!!
        findOrder.payStatus shouldBe "complete"
    }

    test("runtime exception") {
        val order = Order(username = "exception")
        try {
            orderService.order(order)
        } catch (e: Exception) {
            val findOrder = orderRepository.findByIdOrNull(order.id)
            findOrder shouldBe null
        }
    }

    test("business exception") {
        val order = Order(username = "not-enough-money")
        try {
            orderService.order(order)
        } catch (e: Exception) {
            val findOrder = orderRepository.findByIdOrNull(order.id)!!
            findOrder.payStatus shouldBe "wait"
        }
    }
})