package com.noah.jpashop.domain

import com.noah.jpashop.domain.item.Item
import jakarta.persistence.*

@Entity
class OrderItem(
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "order_item_id")
    @Id val id: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: Item,

    @ManyToOne
    @JoinColumn(name = "order_id")
    val order: Order,

    val orderPrice: Int,

    val count: Int

)