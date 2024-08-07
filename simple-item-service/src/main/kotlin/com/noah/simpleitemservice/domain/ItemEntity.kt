package com.noah.simpleitemservice.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "item")
class ItemEntity(
    @Column(name = "item_name", length = 10)
    val itemName: String,
    val price: Int,
    val quantity: Int,

    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,
)
