package com.noah.datajpa.lock.service

import com.noah.datajpa.lock.domain.Stock
import com.noah.datajpa.lock.repository.StockRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class StockServiceTest {

    @Autowired
    private lateinit var stockService: StockService

    @Autowired
    private lateinit var stockRepository: StockRepository

    var id: Long? = null

    @BeforeEach
    fun setUp() {
        id = stockRepository.saveAndFlush(Stock(productId = 1L, quantity = 100)).id
    }

    @Test
    fun decrease() {
        // Given
        val quantity = 1

        // When
        stockService.decrease(id!!, quantity)

        // Then
        Assertions.assertEquals(stockRepository.findByIdOrNull(id!!)!!.quantity, 99)
    }

    @Test
    fun decreaseAsynchronously() {

        val threadCnt = 100
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(100)
        for (i in 0 until threadCnt) {
            executorService.submit {
                try {
                    stockService.decrease(id!!, 1)
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()
        Assertions.assertEquals(stockRepository.findByIdOrNull(id!!)!!.quantity, 0)
    }
}
