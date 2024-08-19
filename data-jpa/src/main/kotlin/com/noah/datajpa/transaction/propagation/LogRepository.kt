package com.noah.datajpa.transaction.propagation

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class LogRepository(
    private val em: EntityManager
) {

//    @Transactional
    fun save(log: Log) {
        em.persist(log)

        if (log.message.contains("error")) {
            throw RuntimeException("error")
        }
    }

    fun find(message: String): Log? {
        return em.createQuery("select l from Log l where l.message = :message", Log::class.java)
            .setParameter("message", message)
            .resultList
            .firstOrNull()
    }
}