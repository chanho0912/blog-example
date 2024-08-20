package com.noah.datajpa.transaction.propagation

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class MemberRepository(
    private val em: EntityManager
) {

    @Transactional
    fun save(member: Member) {
        em.persist(member)
    }

    fun find(username: String): Member? {
        return em.createQuery("select m from Member m where m.username = :username", Member::class.java)
            .setParameter("username", username)
            .resultList
            .firstOrNull()
    }
}