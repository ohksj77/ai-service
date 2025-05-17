package com.assignment.ai_service.config.security

import com.assignment.ai_service.domain.member.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserAuthDetails(
    val member: Member,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    val name: String = member.email
    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
    override fun getPassword(): String = member.password
    override fun getUsername(): String = member.id.toString()
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = member.id != null
    fun getMemberId(): Long = member.id!!
}
