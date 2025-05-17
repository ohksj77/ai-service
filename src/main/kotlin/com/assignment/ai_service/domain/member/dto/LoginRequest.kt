package com.assignment.ai_service.domain.member.dto

import jakarta.validation.constraints.Email
import org.hibernate.validator.constraints.Length

data class LoginRequest(
    @field:Email
    var email: String? = null,
    @field:Length(min = 1, max = 20)
    var password: String? = null,
) {
}
