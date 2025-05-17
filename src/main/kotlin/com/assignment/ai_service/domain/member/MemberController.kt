package com.assignment.ai_service.domain.member

import com.assignment.ai_service.config.security.jwt.JwtToken
import com.assignment.ai_service.domain.member.dto.CredentialRequest
import com.assignment.ai_service.domain.member.dto.LoginRequest
import com.assignment.ai_service.global.dto.IdResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("members")
class MemberController(
    private val memberService: MemberService,
) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("signup")
    fun signup(
        @Valid @RequestBody request: CredentialRequest,
    ): IdResponse = memberService.signup(request)

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("login")
    fun login(
        @Valid @RequestBody request: LoginRequest,
    ): JwtToken = memberService.login(request)
}
