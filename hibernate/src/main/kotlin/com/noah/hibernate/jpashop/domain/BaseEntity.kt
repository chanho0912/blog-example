package com.noah.hibernate.jpashop.domain

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.ZonedDateTime

@MappedSuperclass
abstract class BaseEntity(
    @Column(name = "created_by")
    val createdBy: String = "",
    @Column(name = "last_modified_by")
    val lastModifiedBy: String = "",
    @Column(name = "created_at")
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
    @Column(name = "last_modified_at")
    val lastModifiedAt: ZonedDateTime = ZonedDateTime.now(),
)