package com.noah.simpleitemservice.repository.v2

import com.noah.simpleitemservice.domain.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepositoryV2 : JpaRepository<ItemEntity, Long>
