package com.noah.hibernate

import jakarta.persistence.*

/**
 * 연관관계의 주인(Owner)
 * - 외래 키가 있는 곳을 주인으로 정해야 함.
 *
 * 양방향 매핑 규칙
 * - 객체의 두 관계 중 하나를 연관관계의 주인으로 지정
 * - 연관관계의 주인만이 외래 키를 관리(등록, 수정)
 * - 주인이 아닌 쪽은 읽기만 가능
 * - 주인은 mappedBy 속성 사용 x
 * - 주인이 아니면 mappedBy 속성으로 주인 지정
 *
 * 처음 설계 시점에는 단방향 매핑만으로도 충분하다. (xToOne 관계로 설정)
 * 양방향 매핑은 반대 방향으로 조회 (객체 그래프 탐색) 기능이 추가된 것 뿐
 * JPQL에서 역방향으로 탐색할 일이 많다.
 * 단방향 매핑을 잘 하고 양방향은 필요할 때 추가해도 된다. (테이블에 영향을 주지 않음)
 */
@Entity
@Table(name = "member_jpa")
class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null,

    @Column(name = "username")
    var username: String
)