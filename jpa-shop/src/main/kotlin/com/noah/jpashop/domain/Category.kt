package com.noah.jpashop.domain

import com.noah.jpashop.domain.item.Item
import jakarta.persistence.*

@Entity
class Category(
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "category_id")
    @Id val id: Long = 0L,

    val name: String,

    @ManyToMany
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    val items: Set<Item> = mutableSetOf(),

    @ManyToOne
    @JoinColumn(name = "parent_id")
    val parent: Category,

    @OneToMany(mappedBy = "parent")
    val children: Set<Category> = mutableSetOf()
)