package com.noah.jpashop.domain

import jakarta.persistence.*

@Entity
class Delivery (
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "delivery_id")
    @Id
    val id: Long = 0L,

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    var order: Order,

    @Embedded
    val address: Address,

    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus
)

enum class DeliveryStatus {
    READY, COMP
}