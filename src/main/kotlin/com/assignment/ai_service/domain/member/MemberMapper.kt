package com.assignment.ai_service.domain.member

import com.assignment.ai_service.domain.member.dto.CredentialRequest
import org.springframework.stereotype.Component

@Component
class MemberMapper {

    fun toEntity(credentialRequest: CredentialRequest) = Member(
        email = credentialRequest.email!!,
        password = credentialRequest.password!!,
        name = credentialRequest.name!!,
    )
}
