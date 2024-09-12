package com.noah.hibernate.jpashop.proxy

import jakarta.persistence.*

@Entity
open class Child(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,
    open var name: String,
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    open var parent: Parent?
) {

    constructor() : this(
        id = 0L,
        name = "",
        parent = null
    )
}