package com.noah.hibernate.jpashop.domain

import jakarta.persistence.*

@Entity
@Table(name = "member_jpa_3")
class MemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long = 0L,

    val name: String,
    val city: String,
    val street: String,
    val zipcode: String,

    @OneToMany(mappedBy = "member")
    val orders: MutableList<OrderEntity> = mutableListOf()
)