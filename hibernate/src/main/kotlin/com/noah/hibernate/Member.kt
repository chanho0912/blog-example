package com.noah.hibernate

import jakarta.persistence.*

@Entity
@Table(name = "member_jpa")
class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long = 0L,

    @Column(name = "username")
    var username: String,

//    @Column(name = "team_id")
//    var teamId: Long
    @ManyToOne
    @JoinColumn(name = "team_id")
    var team: Team
)