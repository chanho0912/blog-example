package com.noah.hibernate

import jakarta.persistence.Persistence

class HibernateApplication

fun main(args: Array<String>) {

    // EntityManagerFactory는 애플리케이션 실행 시점에 한번만 생성되어야 한다.
    Persistence.createEntityManagerFactory("noah").use { emf ->
        // EntityManager는 데이터베이스와의 트랜잭션마다 생성되어야 한다.
        // 스레드 간 공유 x 사용하고 버려야함.
        // JPA에서 모든 데이터 변경 단위는 트랜잭션으로 묶여야 한다.
        emf.createEntityManager().use { em ->
            val transaction = em.transaction
            transaction.begin()

            try {
                val member = em.find(Member::class.java, 1L)
                member.name = "HelloJPA"
//                val member = Member(id = 1L, name = "noah")
//                em.persist(member)
                transaction.commit()
            } catch (e: Exception) {
                transaction.rollback()
            }
        }
    }
}