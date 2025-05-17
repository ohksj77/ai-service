package com.assignment.ai_service.config.security.jwt

data class JwtToken(
    val accessToken: String,
    val refreshToken: String,
)
