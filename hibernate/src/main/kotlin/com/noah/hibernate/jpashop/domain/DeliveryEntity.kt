package com.noah.hibernate.jpashop.domain

import jakarta.persistence.*

@Entity
@Table(name = "delivery")
class DeliveryEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    val id: Long = 0L,

    val city: String,
    val street: String,
    val zipcode: String,

    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus,

    @OneToOne(mappedBy = "delivery")
    val order: OrderEntity
)

enum class DeliveryStatus {
    READY, COMP
}