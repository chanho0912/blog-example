package com.noah.hibernate.jpashop.proxy

import jakarta.persistence.*

@Entity
open class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "name")
    var name: String = ""
}