package com.assignment.ai_service.config.web

import com.assignment.ai_service.config.security.jwt.JwtTokenProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AdminInterceptor(
    private val jwtTokenProvider: JwtTokenProvider,
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val authHeader = request.getHeader(TOKEN_HEADER)
        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
            return false
        }

        val token = authHeader.substring(TOKEN_PREFIX_LENGTH)
        if (!jwtTokenProvider.isAdmin(token)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not Admin")
            return false
        }
        return true
    }

    companion object {
        private const val TOKEN_HEADER: String = "Authorization"
        private const val TOKEN_PREFIX: String = "Bearer "
        private const val TOKEN_PREFIX_LENGTH: Int = TOKEN_PREFIX.length
    }
}
