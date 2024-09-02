package com.noah.hibernate

import jakarta.persistence.*
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

/**
 * GenerationType.IDENTITY인 경우 em.persist()를 호출하면 즉시 insert 쿼리가 실행된다.
 * - 영속성 컨텍스트에 저장된 엔티티의 식별자를 알 수 없기 때문에 즉시 insert 쿼리를 실행한다.
 * GenerationType.SEQUENCE인 경우 em.persist()를 호출하면 insert 쿼리는 실행되지 않고, sequence에서 값을 가져온다.
 * - allocationSize: 시퀀스 한 번 호출에 증가하는 수 (default 50)
 * - initialValue: 시작 값 (default 1)
 * - allocationSize를 50으로 설정하면 1, 51, 101, ... 순으로 증가한다. 그 사이 값을 memory에서 사용. (추가 호출 x)
 * GenerationType.TABLE인 경우 em.persist()를 호출하면 insert 쿼리는 실행되지 않고, table에서 값을 가져온다.
 *
 */
@Entity
@Table(name = "member_jpa_2")
class Member2(

    @Id
    // PK가 자동으로 생성되는 경우에는 0L로 초기화해도 된다.

    /**
     * GeneratedValue
     * - strategy: GenerationType.IDENTITY, GenerationType.SEQUENCE, GenerationType.TABLE, GenerationType.AUTO
     * - generator: 사용할 generator 이름
     * - strategy = GenerationType.IDENTITY: auto_increment 기본 키 생성 전략을 DB에 위임
     * - strategy = GenerationType.SEQUENCE: sequence
     * - strategy = GenerationType.TABLE: 테이블
     * - strategy = GenerationType.AUTO: DB에 따라 자동으로 선택
     * - strategy = GenerationType.UUID: UUID
     * - GenerationType.AUTO는 DB에 따라 IDENTITY, SEQUENCE, TABLE 중 하나를 선택한다.
     *
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    /**
     * Column
     * - name: DB의 컬럼명
     * - nullable: null 허용 여부
     * - length: 문자열 길이
     * - precision: 소수점 이하 자릿수
     * - scale: 소수점 이하 자릿수
     * - unique: 유니크 여부 (해당 값을 사용하면 이상한 random 이름이 지어짐, uniqueConstraints로 대체 가능)
     * - columnDefinition: 컬럼 정의
     * - table: 테이블명
     * - insertable: insert 가능 여부
     * - updatable: update 가능 여부
     * Column이 붙으면 default nullable = true이다.
     */
    @Column(name = "name", nullable = false, columnDefinition = "varchar(100) default 'EMPTY'")
    var username: String,

    /**
     * Column이 없으면 not null로 생성.
     */
//    @Column
    var age: Int,

    /**
     * Enumerated
     * - EnumType.ORDINAL: Enum의 순서를 DB에 저장
     * - EnumType.STRING: Enum의 이름을 DB에 저장
     * - EnumType.ORDINAL을 사용하면 Enum의 순서가 바뀌면 DB에 저장된 값이 바뀌므로 EnumType.STRING을 사용하는 것이 좋다.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    var roleType: RoleType,

    // Date, Calendar
    /**
     * Temporal
     * - TemporalType.DATE: 날짜
     * - TemporalType.TIME: 시간
     * - TemporalType.TIMESTAMP: 날짜와 시간
     *
     * LocalDateTime, LocalDate, LocalTime을 사용하면 @Temporal을 사용하지 않아도 된다.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    var createdDate: Date = Date(),

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    var lastModifiedDate: Date = Date(),

    @Column(name = "test_local_date")
    var testLocalDate: LocalDate,

    @Column(name = "test_local_date_time")
    var testLocalDateTime: LocalDateTime,

    // datetime(6)으로 생성됨
    @Column(name = "test_date")
    var testDate: Date,

    // datetime(6)으로 생성됨
    @Column(name = "test_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    var testTimestamp: Timestamp,

    @Lob
    var description: String

    // @Transient: DB에 저장하지 않음
)

enum class RoleType {
    ADMIN, USER
}