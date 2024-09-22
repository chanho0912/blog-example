package com.noah.hibernate.jpql

import jakarta.persistence.*

@Entity(name = "JPQLExampleMember")
@Table(name = "jpql_member")
open class JPQLExampleMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null
    @Column
    open var username: String? = null
    @Column
    open var age: Int? = null

    @ManyToOne
    @JoinColumn(name = "team_id")
    open var team: JPQLExampleTeam? = null
}