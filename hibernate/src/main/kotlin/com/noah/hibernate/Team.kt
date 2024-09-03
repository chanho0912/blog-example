package com.noah.hibernate

import jakarta.persistence.*

@Entity
@Table(name = "team")
class Team(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    val id: Long = 0L,

    var name: String
)