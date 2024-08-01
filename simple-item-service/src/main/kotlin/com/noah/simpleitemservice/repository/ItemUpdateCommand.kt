package com.noah.simpleitemservice.repository

data class ItemUpdateCommand(
    val itemName: String,
    val price: Int,
    val quantity: Int,
)
