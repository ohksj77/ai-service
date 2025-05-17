package com.assignment.ai_service.config.security.jwt

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.Key


@Configuration
class JwtKeyConfig(@param:Value("\${token.secret-key}") private val secretKey: String) {
    @Bean
    fun key(): Key {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }
}
