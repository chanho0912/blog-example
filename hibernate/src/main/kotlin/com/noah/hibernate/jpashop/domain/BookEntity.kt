package com.noah.hibernate.jpashop.domain

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "book")
@DiscriminatorValue("book")
class BookEntity(
    val author: String,
    val isbn: String,
) : ItemEntity2()