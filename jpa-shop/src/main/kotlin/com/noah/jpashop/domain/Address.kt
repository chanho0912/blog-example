package com.noah.jpashop.domain

import jakarta.persistence.Embeddable

@Embeddable
class Address(
    val city: String,
    val street: String,
    val zipcode: String
)