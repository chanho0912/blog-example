package com.noah.jpashop

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class MemberRepository(
    private val em: EntityManager
) {
    @Transactional
    fun save(member: Member): Long {
        em.persist(member)
        return member.id
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): Member {
        return em.find(Member::class.java, id)
    }
}