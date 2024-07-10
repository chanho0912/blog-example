package com.noah.datajpa.lock.service

import com.noah.datajpa.lock.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PessimisticLockStockService(
    private val repository: StockRepository
) {

    @Transactional
    fun decrease(id: Long, quantity: Int) {
        val stock = repository.findByIdWithPessimisticLock(id)

        stock.decrease(quantity)

        repository.save(stock)
    }
}
