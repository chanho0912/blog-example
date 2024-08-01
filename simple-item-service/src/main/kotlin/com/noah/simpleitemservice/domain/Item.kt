package com.noah.simpleitemservice.domain

data class Item(
    var id: Long = 0L,
    val itemName: String,
    val price: Int,
    val quantity: Int
)
