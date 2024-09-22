package com.noah.hibernate.jpql

import jakarta.persistence.*

@Entity(name = "JPQLExampleTeam")
@Table(name = "jpql_team")
open class JPQLExampleTeam {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null
    @Column
    open var name: String? = null

    @OneToMany(mappedBy = "team")
    open var members: MutableList<JPQLExampleMember> = mutableListOf()

}