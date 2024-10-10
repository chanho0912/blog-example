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

    val orderedAt: ZonedDateTime,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus,
) {
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItems: MutableSet<OrderItem> = mutableSetOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null
        set(value) {
            field = value
            value?.orders?.add(this)
        }

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery? = null
        set(value) {
            field = value
            value?.order = this
        }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }
}

enum class OrderStatus {
    ORDER, CANCEL
}