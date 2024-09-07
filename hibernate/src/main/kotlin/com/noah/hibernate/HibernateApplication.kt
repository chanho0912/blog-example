package com.noah.hibernate

import com.noah.hibernate.jpashop.domain.MovieEntity
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
 *
 * 연관관계 매핑시 고려사항 3가지
 * - 다중성
 * - 단방향, 양방향
 * -- 테이블
 * --- 외래키 하나로 양쪽 조인 가능
 * --- 사실 방향이라는 개념이 없음
 * -- 객체
 * --- 참조용 필드가 있는 쪽이 연관관계의 주인
 * --- 한쪽만 참조하면 단방향
 * --- 양쪽이 서로 참조하면 양방향
 * - 연관관계의 주인
 * -- 테이블은 외래키 하나로 두 테이블의 연관관계를 관리
 * -- 객체 양방향 관계는 참조가 2군데
 * -- 둘 중 테이블의 외래키를 관리할 곳을 지정해야함
 * -- 주인만이 외래키를 관리(등록, 수정)
 * -- 주인이 아닌쪽은 읽기만, 가능 영향을 주지 않음
 *
 * 일대일 관계
 * - 주 테이블이나 대상 테이블 중 외래키 선택 가능
 * - 외래키에 데이터베이스 유니크 제약조건 추가
 *
 * 다대다 관계
 * - 관계형 데이터베이스는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없음
 * - 연결 테이블을 추가해서 일대다, 다대일 관계로 풀어내야함
 * - @ManyToMany 사용 x 그냥 쓰지 말고 중간 테이블을 엔티티로 승격해서 사용 (연결 테이블을 엔티티로 승격)
 *
 * 상속관계 매핑
 * - 관계형 데이터베이스에는 상속 관계 x
 * - 슈퍼타입 서브타입 관계라는 모델링 기법
 * - 상속관계 매핑: 객체의 상속과 관계형 데이터베이스의 슈퍼타입 서브타입 관계를 매핑
 * - 조인 전략
 * 장점: 테이블 정규화, 외래키 참조 무결성 제약조건 활용, 저장공간 효율화
 * 단점: 조회시 조인을 많이 사용, 조회 쿼리가 복잡, 데이터 저장시 insert sql이 두번 실행
 * - 각각의 테이블로 변환 (사용 권장 x)
 * 장점: 서브타입을 명확하게 구분해서 처리할 때 효과적, not null 제약조건 사용 가능
 * 단점: 자식 테이블을 통합해서 쿼리하기 어려움 (UNION ALL SQL 사용)
 * - 단일 테이블 전략 (JPA 기본)
 * 장점: 조인 x, 조회 성능 빠름
 * 단점: 자식 엔티티가 매핑한 컬럼은 모두 null 허용
 * 기본은 Join, 테이블이 너무 단순할 때 Single Table 고려하는 방향으로
 *
 * @MappedSuperclass
 * - 공통 매핑 정보가 필요할 때 사용
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
                val movie = MovieEntity(
                    director = "director",
                    actor = "actor",
                    name = "name",
                    price = 10000
                )
                // 저장할때는 두번 insert
                em.persist(movie)

                em.flush()
                em.clear()

                // 읽을때는 Join
                val findMovie = em.find(MovieEntity::class.java, movie.id)
                println(findMovie)

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