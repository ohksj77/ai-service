package com.assignment.ai_service.domain.member

import com.assignment.ai_service.global.BaseEntity
import jakarta.persistence.Entity
import org.hibernate.annotations.SoftDelete

@Entity
@SoftDelete
class Member(
    val email: String,
    val password: String,
    val name: String,
    val role: Role = Role.ROLE_MEMBER,
) : BaseEntity() {
}
