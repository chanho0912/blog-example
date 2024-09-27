package com.noah.hibernate

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

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
open class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    open val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    open var team: Team? = null,

    @Column(name = "username")
    open var username: String,

    // 값 타입 컬렉션 -> 값 타입을 하나 이상 저장할 때 사용
    // 값 타입은 생명주기를 엔티티에 의존
    // 값 타입 컬렉션은 영속성 전이 + 고아 객체 제거 기능을 필수로 가진다.
    // 값 타입 컬렉션은 기본이 Lazy로 조회한다.
    // 값 타입은 엔티티와 다르게 식별자 개념이 없다.
    // 값은 변경하면 추적이 어렵다.
    // 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다.
//    @ElementCollection
//    @CollectionTable(
//        name = "favorite_food",
//        joinColumns = [JoinColumn(name = "member_id")]
//    )
//    @Column(name = "food_name")
//    open var favoriteFoods: MutableSet<String> = mutableSetOf(),
//
//    @ElementCollection
//    @CollectionTable(
//        name = "address",
//        joinColumns = [JoinColumn(name = "member_id")]
//    )
//    open var addressHistory: MutableList<Address> = mutableListOf(),
//
//    @Embedded
//    open var address: Address? = null
)

@Embeddable
data class Address(
    var city: String,
    var street: String,
    var zipcode: String
) {
    constructor() : this("", "", "")
}

@Embeddable
data class Period(
    var startDate: LocalDateTime,
    var endDate: LocalDateTime
) {
    constructor() : this(LocalDateTime.now(), LocalDateTime.now())
}