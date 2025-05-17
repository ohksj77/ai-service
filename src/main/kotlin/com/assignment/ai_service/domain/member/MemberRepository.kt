package com.assignment.ai_service.domain.member

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Member?
}
