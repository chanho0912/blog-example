package com.noah.hibernate

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "member_jpa")
class Member(
    @Id
    val id: Long = 0L,
    var name: String
)