package com.noah.hibernate.jpashop.domain

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "album")
@DiscriminatorValue("album")
class AlbumEntity (
    val artist: String,
) : ItemEntity2()