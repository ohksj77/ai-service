package com.assignment.ai_service.domain.member

import com.assignment.ai_service.config.security.jwt.JwtToken
import com.assignment.ai_service.domain.member.dto.CredentialRequest
import com.assignment.ai_service.domain.member.dto.LoginRequest
import com.assignment.ai_service.global.dto.IdResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val authService: AuthService,
    private val memberMapper: MemberMapper,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun signup(credentialRequest: CredentialRequest): IdResponse {
        validateMemberExists(credentialRequest)
        credentialRequest.password = passwordEncoder.encode(credentialRequest.password!!)
        val member: Member = memberRepository.save(memberMapper.toEntity(credentialRequest))

        return IdResponse(member.id!!)
    }

    private fun validateMemberExists(credentialRequest: CredentialRequest) {
        if (isMemberExists(credentialRequest.email!!)) {
            throw IllegalArgumentException("Email already exists")
        }
    }

    private fun isMemberExists(email: String): Boolean {
        return memberRepository.existsByEmail(email)
    }

    @Transactional(readOnly = true)
    fun login(loginRequest: LoginRequest): JwtToken {
        val member = getMemberByEmail(loginRequest)
        validatePassword(loginRequest, member)

        return authService.createJwtToken(member)
    }

    fun validatePassword(loginRequest: LoginRequest, member: Member) {
        if (!passwordEncoder.matches(loginRequest.password!!, member.password)) {
            throw IllegalArgumentException("Invalid password")
        }
    }

    private fun getMemberByEmail(loginRequest: LoginRequest): Member {
        return memberRepository
            .findByEmail(loginRequest.email!!) ?: throw IllegalArgumentException("Member not found")
    }

    fun getEntity(id: Long): Member =
        memberRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("Member not found")
}
