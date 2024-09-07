package com.noah.hibernate.jpashop.domain

import jakarta.persistence.*

@Entity
@Table(name = "order_item")
class OrderItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    val id: Long = 0L,

//    @Column(name = "order_id")
    @ManyToOne
    // JoinColumn은 Order에 있을 수도 있음. 다만 그러면 주인이 외래키가 없는 쪽이 되어 Query가 추가로 발생(Update)
    // 그래서 일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하자.(필요 하다면...)
    @JoinColumn(name = "order_id")
    val order: OrderEntity,

//    @Column(name = "item_id")
    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: ItemEntity,

    @Column(name = "order_price")
    val orderPrice: Int,

    val count: Int
)