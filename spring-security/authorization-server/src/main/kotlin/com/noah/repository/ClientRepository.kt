package com.noah.repository

import com.noah.entity.ClientEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository : JpaRepository<ClientEntity, Long> {
    fun findByClientId(clientId: String): ClientEntity?
}
