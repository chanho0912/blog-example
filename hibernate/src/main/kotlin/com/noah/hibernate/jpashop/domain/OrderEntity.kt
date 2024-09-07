package com.noah.hibernate.jpashop.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class OrderEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    val id: Long = 0L,

//    @Column(name = "member_id")
    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: MemberEntity,

    @Column(name = "order_date")
    val orderDate: LocalDateTime,

    @OneToOne
    @JoinColumn(name = "delivery_id")
    val delivery: DeliveryEntity,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus,

    @OneToMany(mappedBy = "order")
    val orderItems: MutableList<OrderItemEntity> = mutableListOf()
)

enum class OrderStatus {
    ORDER, CANCEL
}