package com.noah.jpashop.repository

import com.noah.jpashop.domain.Member
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class MemberRepository(
    private val em: EntityManager
) {

    fun save(member: Member) {
        em.persist(member)
    }

    fun findOne(id: Long): Member {
        return em.find(Member::class.java, id)
    }

    fun findAll(): List<Member> {
        return em.createQuery(
            """
                select m from Member m
            """.trimIndent(),
            Member::class.java
        )
            .resultList
    }

    fun findByName(name: String): List<Member> {
        return em.createQuery(
            """
                select m from Member m
                where m.name = :name
            """.trimIndent(),
            Member::class.java
        )
            .setParameter("name", name)
            .resultList
    }
}