package com.assignment.ai_service.config.security

import com.assignment.ai_service.domain.member.MemberRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class UserAuthDetailsService(
    private val memberRepository: MemberRepository,
    private val authorityManager: AuthorityManager
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return memberRepository
            .findByIdOrNull(username.toLong())
            ?.let { member ->
                UserAuthDetails(
                    member,
                    authorityManager.mapToAuthorities(member.role)
                )
            } ?: throw EntityNotFoundException("User not found with id: $username")
    }
}
