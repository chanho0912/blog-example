package com.noah.datajpa.lock.domain

import com.github.f4b6a3.ulid.UlidCreator
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.*

@Entity
class Stock(
    id: Long = 0L,
    productId: Long,
    quantity: Int
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = id
        protected set

    @Column
    var productId = productId
        protected set

    @Column
    var quantity = quantity
        protected set

    fun decrease(quantity: Int) {
        require(this.quantity > quantity) { "Stock is not enough" }
        this.quantity -= quantity
    }
}
