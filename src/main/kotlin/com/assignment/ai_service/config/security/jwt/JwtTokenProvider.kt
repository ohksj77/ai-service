package com.assignment.ai_service.config.security.jwt

import com.assignment.ai_service.config.security.AuthorityManager
import com.assignment.ai_service.config.security.UserAuthDetailsService
import com.assignment.ai_service.domain.member.Member
import com.assignment.ai_service.domain.member.Role
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider(
    private val authorityManager: AuthorityManager,
    private val userAuthDetailsService: UserAuthDetailsService,
    private val key: Key
) {

    fun validateToken(token: String?) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
        } catch (e: JwtException) {
            val message = when (e) {
                is ExpiredJwtException -> "JWT token expired"
                is UnsupportedJwtException -> "JWT token unsupported"
                else -> "JWT token invalid"
            }
            throw IllegalStateException(message, e)
        }
    }

    fun resolveToken(bearerToken: String?): String? =
        bearerToken?.takeIf { it.startsWith(BEARER_PREFIX) }?.substring(BEARER_PREFIX.length)

    fun createToken(user: Member): JwtToken = JwtToken(
        accessToken = createAccessTokenValue(user),
        refreshToken = createRefreshTokenValue()
    )

    fun isAdmin(accessToken: String): Boolean {
        val claims = parseClaims(accessToken)
        return claims[AUTHORITIES_KEY].toString() == Role.ROLE_ADMIN.name
    }

    private fun createAccessTokenValue(user: Member): String =
        Jwts.builder()
            .setSubject(JWT_SUBJECT)
            .setClaims(createAccessTokenClaims(user))
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_LENGTH))
            .compact()

    private fun createRefreshTokenValue(): String =
        Jwts.builder()
            .setSubject(JWT_SUBJECT)
            .setClaims(Jwts.claims())
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_LENGTH))
            .compact()

    private fun createAccessTokenClaims(user: Member): Claims = Jwts.claims().apply {
        subject = user.getId().toString()
        this[AUTHORITIES_KEY] = user.role.name
    }

    fun getAuthentication(bearerToken: String): Authentication {
        val token = resolveToken(bearerToken) ?: throw IllegalStateException("Invalid token")
        validateToken(token)
        val claims = parseClaims(token)
        val authorities = authorityManager.mapToAuthorities(Role.valueOf(claims[AUTHORITIES_KEY].toString()))
        val userDetails = userAuthDetailsService.loadUserByUsername(claims.subject)
        return UsernamePasswordAuthenticationToken(userDetails, userDetails.password, authorities)
    }

    private fun parseClaims(token: String): Claims =
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        } catch (e: ExpiredJwtException) {
            throw IllegalStateException("JWT token expired", e)
        }

    companion object {
        private const val ACCESS_TOKEN_EXPIRE_LENGTH = 60L * 60 * 1000 // 1 hour
        private const val REFRESH_TOKEN_EXPIRE_LENGTH = 60L * 60 * 24 * 1000 // 24 hour
        private const val JWT_SUBJECT = "jwt-auth"
        private const val AUTHORITIES_KEY = "auth"
        private const val BEARER_PREFIX = "Bearer "
    }
}
