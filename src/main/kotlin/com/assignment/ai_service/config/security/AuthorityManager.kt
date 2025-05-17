package com.assignment.ai_service.config.security

import com.assignment.ai_service.domain.member.Role
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*


@Component
class AuthorityManager {
    private val authorities: MutableMap<Role, SimpleGrantedAuthority> = EnumMap(Role::class.java)

    init {
        authorities[Role.ROLE_ADMIN] = SimpleGrantedAuthority(Role.ROLE_ADMIN.name)
        authorities[Role.ROLE_MEMBER] = SimpleGrantedAuthority(Role.ROLE_MEMBER.name)
    }

    fun mapToAuthorities(role: Role): List<SimpleGrantedAuthority> {
        return listOf(getAuthorityByRole(role))
    }

    private fun getAuthorityByRole(role: Role): SimpleGrantedAuthority {
        return authorities[role] ?: throw IllegalArgumentException("Invalid role: $role")
    }
}
