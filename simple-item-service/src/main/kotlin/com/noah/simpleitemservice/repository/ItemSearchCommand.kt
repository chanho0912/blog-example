package com.noah.simpleitemservice.repository

data class ItemSearchCommand(
    val itemName: String? = null,
    val maxPrice: Int? = null,
)
