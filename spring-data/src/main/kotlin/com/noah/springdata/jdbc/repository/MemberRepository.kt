package com.noah.springdata.jdbc.repository

import com.noah.springdata.jdbc.domain.Member

interface MemberRepository {
    fun save(member: Member): Member
    fun findById(memberId: String): Member
    fun update(memberId: String, money: Int)
    fun delete(memberId: String)
}
