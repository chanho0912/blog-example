package com.noah.hibernate.jpql

import jakarta.persistence.*

@Entity(name = "JPQLExampleProduct")
@Table(name = "jpql_product")
open class JPQLExampleProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Column
    open var name: String? = null

    @Column
    open var price: Int? = null

    @Column(name = "stock_amount")
    open var stockAmount: Int? = null
}