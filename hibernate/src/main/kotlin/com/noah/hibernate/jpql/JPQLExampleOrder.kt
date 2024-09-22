package com.noah.hibernate.jpql

import jakarta.persistence.*

@Entity(name = "JPQLExampleOrder")
@Table(name = "jpql_order")
open class JPQLExampleOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @Column(name = "order_amount")
    open var orderAmount: Int? = null

    @Embedded
    open var address: Address? = null

    @ManyToOne
    @JoinColumn(name = "product_id")
    open var product: JPQLExampleProduct? = null
}

@Embeddable
data class Address(
    val city: String,
    val street: String,
    val zipcode: String
)