package com.noah.hibernate.jpashop.proxy

/**
 * 지연 로딩, Proxy
 *
 * em.find() vs em.getReference()
 * - em.find() : 데이터베이스를 통해서 실제 엔티티 객체 조회
 * - em.getReference() : 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체 조회
 *
 *
 */