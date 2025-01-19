package com.noah.datajpa.transaction

import jakarta.persistence.*

@Entity
@Table(name = "orders_test1")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    var username: String,
    var payStatus: String = ""
)
