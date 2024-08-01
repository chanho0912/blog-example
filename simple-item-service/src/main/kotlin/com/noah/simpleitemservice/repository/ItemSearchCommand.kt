package com.noah.simpleitemservice.repository

data class ItemSearchCommand(
    val itemName: String = "",
    val maxPrice: Int? = null,
)
