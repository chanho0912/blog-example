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
    val items: MutableSet<Item> = mutableSetOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Category,

    @OneToMany(mappedBy = "parent")
    val children: MutableSet<Category> = mutableSetOf()
) {
    fun addChildCategory(child: Category) {
        children.add(child)
        child.parent = this
    }
}