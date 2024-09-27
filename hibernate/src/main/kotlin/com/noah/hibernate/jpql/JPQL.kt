package com.noah.hibernate.jpql

/**
 * JPQL (Java Persistence Query Language)
 * JPQL은 객체 지향 쿼리 언어
 * SQL을 추상화한 객체 지향 쿼리 언어
 * 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
 * JPA를 사용하면 엔티티 객체를 중심으로 개발
 * 문제는 검색 쿼리
 * 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색
 * 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
 * 애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요
 *
 * TypedQuery
 * - 반환 타입이 명확할 때 사용
 * Query
 * - 반환 타입이 명확하지 않을 때 사용
 *
 * 결과 조회 API
 * - query.getResultList(): 결과가 하나 이상일 때, 리스트 반환
 * - query.getSingleResult(): 결과가 정확히 하나, 단일 객체 반환
 * -- 결과가 없으면: javax.persistence.NoResultException
 * -- 둘 이상이면: javax.persistence.NonUniqueResultException
 *
 * Projection
 * Select 절에 조회할 대상을 지정하는 것
 * 프로젝션 대상: 엔티티, 임베디드 타입, 스칼라 타입
 *
 * Select m from Member m -> 엔티티 프로젝션
 * Select m.team from Member m -> 엔티티 프로젝션
 * Select m.address from Member m -> 임베디드 타입 프로젝션
 * Select m.username, m.age from Member m -> 스칼라 타입 프로젝션
 * DISTINCT로 중복 제거
 *
 * 페이징 API
 * setFirstResult(int startPosition): 조회 시작 위치 (0부터 시작)
 * setMaxResults(int maxResult): 조회할 데이터 수
 *
 * JPA 서브 쿼리 한계
 * - JPA는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능
 * - SELECT 절도 가능 (하이버네이트에서 지원)
 * - FROM 절의 서브 쿼리는 현재 JPQL에서 불가능
 * - 조인으로 풀 수 있으면 풀어서 해결
 *
 * 경로 표현식
 * - .(점)을 찍어 객체 그래프를 탐색하는 것
 * - JPQL에서는 상태 필드나 연관 필드를 탐색할 수 있다.
 * - 상태 필드: 단순히 값을 저장하기 위한 필드
 * - 연관 필드: 연관관계를 위한 필드
 * 단일 값 연관 필드: @ManyToOne, @OneToOne
 * 컬렉션 값 연관 필드: @OneToMany, @ManyToMany
 * - 경로 표현식에서 주의할 점은 null 체크
 *
 * 상태 필드(state field): 경로 탐색의 끝, 탐색 x
 * 단일 값 연관 필드: 묵시적 내부 조인 발생, 탐색 O
 * 왠만하면 묵시적 내부 조인이 발생하도록 작성하지 않는다.
 * 컬렉션 값 연관 필드: 묵시적 내부 조인 발생, 탐색 x
 * - FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능
 *
 * 페치 조인 (Fetch Join)
 * - SQL 조인 종류 X
 * - JPQL에서 성능 최적화를 위해 제공하는 기능
 * - 연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회하는 기능
 * - join fetch 명령어 사용
 * - 페치 조인 ::= [ LEFT [OUTER] | INNER ] JOIN FETCH 조인경로
 */