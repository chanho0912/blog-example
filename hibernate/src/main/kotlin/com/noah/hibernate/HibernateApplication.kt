package com.noah.hibernate

import jakarta.persistence.Persistence

/**
 * 영속성 컨텍스트
 *  JPA를 이해하는데 가장 중요한 언어
 *  엔티티를 영구 저장하는 환경
 *  EntityManager.persist(entity); >> DB에 저장하는 것이 아니라 영속성 컨텍스트에 저장
 *
 * EntityManager -> PersistenceContext
 *  비영속 (new/transient) : 영속성 컨텍스트와 전혀 관계가 없는 상태
 *  영속 (managed) : 영속성 컨텍스트에 관리되는 상태
 *  준영속 (detached) : 영속성 컨텍스트에 저장되었다가 분리된 상태
 *  삭제 (removed) : 삭제된 상태
 *
 * 영속성 컨텍스트의 이점
 * 1. 1차 캐시
 * 2. 동일성 보장
 * 3. 트랜잭션을 지원하는 쓰기 지연
 * 4. 변경 감지
 * 5. 지연 로딩
 *
 * flush를 하더라도 영속성 컨텍스트에 있는 데이터는 지워지지 않는다.
 * 영속성 컨텍스트를 플러시하면 변경 감지 기능이 동작해서 수정된 엔티티를 찾아서 쿼리를 생성하고 DB에 반영한다.
 */
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

            // 양방향 연관관계를 설정할 때는 순수 객체 상태를 고려하여 양쪽에 값을 설정해야 한다.
            // 1. flush, clear를 하지 않으면 1차 캐시에 저장된 데이터를 사용한다.
            // 2. test시에 JPA를 사용하지 않으면 온전하지 못한 값으로 테스트를 할 수도 있다.
            try {
//                val member = em.find(Member::class.java, 1L)
//                // 추가 쿼리 x
//                val member1 = em.find(Member::class.java, 1L)
//                println(member == member1)

//                val member = Member(username = "noah", teamId = team.id)

                val team = Team(name = "teamA")
//                team.members.add(member)
                em.persist(team)

                val member = Member(username = "noah")
                member.team = team
                em.persist(member)

//                em.flush()
//                em.clear()

                val findMember = em.find(Member::class.java, member.id)
//                val findTeam = em.find(Team::class.java, findMember.teamId)
                val findTeam = findMember.team
                println("team id=${findMember?.teamId}, team name=${findTeam?.name}")
                val teamMembers = findTeam?.members

                if (teamMembers != null)
                    for (teamMember in teamMembers) {
                        println("teamMember.username=${teamMember.username}")
                    }

                transaction.commit()
            } catch (e: Exception) {
                transaction.rollback()
            }
        }

        // hibernate spec 직접 사용
//        emf.unwrap(SessionFactory::class.java).openSession().use { session ->
//            session.beginTransaction()
//            val member = session.get(Member::class.java, 1L)
//            println(member)
//            session.transaction.commit()
//        }
    }
}