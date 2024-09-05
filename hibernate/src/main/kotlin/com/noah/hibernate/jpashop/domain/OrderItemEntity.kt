package com.noah.hibernate.jpashop.domain

import jakarta.persistence.*

@Entity
@Table(name = "order_item")
class OrderItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    val id: Long = 0L,

//    @Column(name = "order_id")
    @ManyToOne
    @JoinColumn(name = "order_id")
    val order: OrderEntity,

//    @Column(name = "item_id")
    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: ItemEntity,

    @Column(name = "order_price")
    val orderPrice: Int,

    val count: Int
)