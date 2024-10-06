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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    val item: Item,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order,

    val orderPrice: Int,

    val count: Int

)