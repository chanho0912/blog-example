package com.noah.hibernate.jpashop.domain

import jakarta.persistence.*

@Entity
@Table(name = "item_jpa_2")
@Inheritance(strategy = InheritanceType.JOINED)
// 넣는게 좋음. 왜냐하면 운영시에는 DB 쿼리만 사용하는 경우가 있는데 이게 없으면 알 수 없음.
// Single Table 전략을 사용할 때는 자동으로 적용됨.
// Table Per Class 전략을 사용할 때는 적용안됨.( 필요없음)
@DiscriminatorColumn(
    name = "dis_col",
)
abstract class ItemEntity2(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    val id: Long = 0L,

    val name: String = "",
    val price: Int = 0,
)