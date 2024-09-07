package com.noah.hibernate.jpashop.domain

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "movie")
@DiscriminatorValue("movie")
class MovieEntity(
    val director: String,
    val actor: String,
    name: String,
    price: Int,
) : ItemEntity2(
    name = name,
    price = price,
)