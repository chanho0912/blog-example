package com.noah.hibernate.jpashop.domain

import jakarta.persistence.*

/**
 * 일대일 정리
 * 1. 주 테이블에 외래 키
 * - 주 객체가 대상 객체의 참조를 가지는 것 처럼 객체의 참조를 저장하는 방법
 * - 주 테이블에 외래 키를 두고 대상 테이블을 찾는 방법
 * - 객체지향 개발자들이 선호하는 방법
 * - JPA 매핑하기 편리
 * - 장점: 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능
 * - 단점: 값이 없으면 외래 키에 null을 허용해야 함
 * 2. 대상 테이블에 외래 키
 * - 대상 테이블에 외래 키가 존재
 * - DBA 선호
 * - 장점: 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조 유지
 * - 단점: 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시 로딩됨 ********
 * - JPA 입장에서는 Proxy 객체를 만드려면 Locker의 값이 있는 지 없는 지 알아야함. (있으면 Proxy, 없으면 null)
 * - 이 경우에는 Member의 Locker를 조회해도 있는 지 없는 지 판단이 안되기 때문에 항상 Locker를 조회해야함.
 *
 */
@Entity
@Table(name = "locker")
class LockerEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val name: String
)