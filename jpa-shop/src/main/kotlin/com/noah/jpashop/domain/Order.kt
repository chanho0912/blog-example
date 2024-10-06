package com.noah.jpashop.domain

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "orders")
class Order(
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "order_id")
    @Id val id: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member,

    val orderedAt: ZonedDateTime,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus,

    @OneToMany(mappedBy = "order")
    val orderItems: Set<OrderItem> = mutableSetOf(),

    @OneToOne
    @JoinColumn(name = "delivery_id")
    val delivery: Delivery
)

enum class OrderStatus {
    ORDER, CANCEL
}