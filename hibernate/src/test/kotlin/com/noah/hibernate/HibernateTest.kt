//package com.noah.hibernate
//
//import jakarta.persistence.Persistence
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Test
//
//class HibernateTest {
//
//    @Test
//    fun `persist 호출 후 member에 team을 세팅하면 dirty checking이 동작하며 flush에서 update 발생하지만 commit 전에 영속성 컨텍스트 값에 반영되지 않는다`() {
//        Persistence.createEntityManagerFactory("noah").use { emf ->
//            emf.createEntityManager().use { em ->
//                val transaction = em.transaction
//                transaction.begin()
//
//                val team = Team(name = "teamA")
//                em.persist(team)
//
//                val member = Member(username = "noah")
//                em.persist(member)
//
////                member.team = team
//
//                println("=============before flush=============")
//                em.flush()
//                println("=============after flush=============")
//
////                val memberInContext = em.find(Member::class.java, member.id)
////                Assertions.assertNotNull(memberInContext.team)
////                val teamInContext = em.find(Team::class.java, team.id)
////                Assertions.assertTrue(teamInContext.members.isEmpty())
////
////                transaction.commit()
//            }
//        }
//    }
//
//    @Test
//    fun `persist 호출 후 member에 team을 세팅하면 dirty checking이 동작하며 flush에서 update 발생하지고 영속성 컨텍스트를 초기화 한 뒤 다시 조회하면 team에 member가 셋팅된다`() {
//        Persistence.createEntityManagerFactory("noah").use { emf ->
//            emf.createEntityManager().use { em ->
//                val transaction = em.transaction
//                transaction.begin()
//
//                val team = Team(name = "teamA")
//                em.persist(team)
//
//                val member = Member(username = "noah")
//                em.persist(member)
//
////                member.team = team
////
////                println("=============before flush=============")
////                em.flush()
////                em.clear()
////                println("=============after flush=============")
////
////                val memberInContext = em.find(Member::class.java, member.id)
////                Assertions.assertNotNull(memberInContext.team)
////                val teamInContext = em.find(Team::class.java, team.id)
////                Assertions.assertTrue(teamInContext.members.isNotEmpty())
////
////                transaction.commit()
//            }
//        }
//    }
//
//    @Test
//    fun `persist 호출 후 team에 member를 세팅하면 dirty checking이 동작하지 않으며 flush에서 update 발생하지 않는다`() {
//        Persistence.createEntityManagerFactory("noah").use { emf ->
//            emf.createEntityManager().use { em ->
//                val transaction = em.transaction
//                transaction.begin()
//
//                val team = Team(name = "teamA")
//                em.persist(team)
//
//                val member = Member(username = "noah")
//                em.persist(member)
//
//                team.members.add(member)
//
//                println("=============before flush=============")
//                em.flush()
//                println("=============after flush=============")
//
//                val memberInCache = em.find(Member::class.java, member.id)
//                Assertions.assertNull(memberInCache.team)
//
//                transaction.commit()
//            }
//        }
//    }
//}
