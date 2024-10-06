package com.noah.jpashop.domain.item

import com.noah.jpashop.domain.Category
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dis_type")
abstract class Item(
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "item_id")
    @Id val id: Long = 0L,

    val name: String = "",

    val price: Int = 0,

    val stockQuantity: Int = 0,

    @ManyToMany(mappedBy = "items")
    val categories: Set<Category> = mutableSetOf()
)