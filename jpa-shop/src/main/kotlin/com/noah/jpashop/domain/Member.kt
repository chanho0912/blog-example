package com.noah.jpashop.domain

import jakarta.persistence.*

@Entity
class Member(
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "member_id")
    @Id val id: Long = 0L,

    @Column(length = 63)
    val name: String = "",

    @Embedded
    val address: Address,

    @OneToMany(
        mappedBy = "member",
//        cascade = [CascadeType.ALL],
//        orphanRemoval = true
    )
    val orders: MutableSet<Order> = mutableSetOf()
)