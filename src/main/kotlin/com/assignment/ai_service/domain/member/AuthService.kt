package com.assignment.ai_service.domain.member

import com.assignment.ai_service.config.security.UserAuthDetails
import com.assignment.ai_service.config.security.jwt.JwtToken
import com.assignment.ai_service.config.security.jwt.JwtTokenProvider
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service


@Service
class AuthService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository
) {

    fun createJwtToken(member: Member): JwtToken {
        return jwtTokenProvider.createToken(member)
    }

    fun getLoginUserId(): Long = getuserAuthDetails().username.toLong()

    fun getuserAuthDetails(): UserAuthDetails =
        (SecurityContextHolder.getContext().authentication.principal) as UserAuthDetails

    fun getLoginUser(): Member =
        memberRepository
            .findByIdOrNull(getLoginUserId()) ?: throw EntityNotFoundException()
}
