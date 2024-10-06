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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,

    val orderedAt: ZonedDateTime,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItems: MutableSet<OrderItem> = mutableSetOf(),

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery
) {
    fun setMember(member: Member) {
        member.orders.add(this)
        this.member = member
    }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }

    fun setDelivery(delivery: Delivery) {
        delivery.order = this
        this.delivery = delivery
    }
}

enum class OrderStatus {
    ORDER, CANCEL
}