package com.example.kotlinsecurity.member.repository

import com.example.kotlinsecurity.member.entity.Member
import com.example.kotlinsecurity.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?
}

interface MemberRoleRepository: JpaRepository<MemberRole, Long>