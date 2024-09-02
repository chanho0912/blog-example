package com.noah.hibernate.jpashop.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class OrderEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    val id: Long = 0L,

    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "order_date")
    val orderDate: LocalDateTime,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus
)

enum class OrderStatus {
    ORDER, CANCEL
}