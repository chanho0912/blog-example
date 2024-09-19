package com.noah.hibernate

import jakarta.persistence.*

/**
 * 테이블 연관관계를 보면 양방향, 단방향의 차이가 없다.
 * Member에서는 team_id를 사용해서 조인, Team에서는 team_id를 사용해서 조인한다.
 * 테이블의 연관관계는 외래키 하나로 양방향이 다 존재하게 된다.
 *
 */
@Entity
@Table(name = "team")
open class Team(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    open val id: Long = 0L,

    open var name: String,

    // 나는 team으로 mapping 되어 있다.
    @OneToMany(mappedBy = "team")
    open val members: MutableList<Member> = mutableListOf()
)