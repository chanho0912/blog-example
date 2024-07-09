package com.noah.datajpa.lock.service

import com.noah.datajpa.lock.repository.StockRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class StockService(
    private val stockRepository: StockRepository
) {

    @Transactional
    fun decrease(id: Long, quantity: Int) {
        val stock = stockRepository.findByIdOrNull(id)
            ?: run {
                // logging
                throw IllegalArgumentException("Stock not found")
            }

        stock.decrease(quantity)
        stockRepository.save(stock)
    }

}
