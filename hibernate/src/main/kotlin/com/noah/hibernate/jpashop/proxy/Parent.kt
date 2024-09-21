package com.noah.hibernate.jpashop.proxy

import jakarta.persistence.*

@Entity
open class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Column(name = "name")
    open var name: String = ""

    @OneToMany(
        mappedBy = "parent",
//        cascade = [CascadeType.ALL],
//        orphanRemoval = true
    )
    open val children: MutableList<Child> = mutableListOf()

    fun addChild(child: Child) {
        children.add(child)
        child.parent = this
    }
}