package com.noah.datajpa.lock.repository

import com.noah.datajpa.lock.domain.Stock
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<Stock, Long> {
}
