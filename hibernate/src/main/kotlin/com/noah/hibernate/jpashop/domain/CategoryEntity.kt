package com.noah.hibernate.jpashop.domain

import jakarta.persistence.*

@Entity
@Table(name = "category")
class CategoryEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    val id: Long = 0L,

    val name: String,

    @ManyToOne
    @JoinColumn(name = "parent_id")
    var parent: CategoryEntity? = null,

    @OneToMany(mappedBy = "parent")
    val child: MutableList<CategoryEntity> = mutableListOf(),

    @ManyToMany
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    val items: MutableList<ItemEntity> = mutableListOf()
)