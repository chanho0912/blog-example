package com.noah.datajpa.lock.service

import com.noah.datajpa.lock.domain.Stock
import com.noah.datajpa.lock.repository.StockRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@SpringBootTest
class StockServiceTest {

    @Autowired
    private lateinit var stockService: StockService

    @Autowired
    private lateinit var stockRepository: StockRepository

    var id: Long? = null

    @BeforeEach
    fun setUp() {
        id = stockRepository.saveAndFlush(Stock(productId = 1L, quantity = 10)).id
    }

    @Test
    fun decrease() {
        // Given
        val quantity = 5

        // When
        stockService.decrease(id!!, quantity)

        // Then
        Assertions.assertEquals(stockRepository.findByIdOrNull(id!!)!!.quantity, 5)
    }
}
