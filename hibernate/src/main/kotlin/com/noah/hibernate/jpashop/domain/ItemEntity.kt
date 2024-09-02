package com.noah.hibernate.jpashop.domain

import jakarta.persistence.*

@Entity
@Table(name = "item")
class ItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id") // spring boot에서는 자동으로 snake case로 변경해준다.
    val id: Long = 0L,

    val name: String,
    val price: Int,

    @Column(name = "stock_quantity")
    val stockQuantity: Int
)