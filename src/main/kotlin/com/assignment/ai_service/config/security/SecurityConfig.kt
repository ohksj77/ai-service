package com.assignment.ai_service.config.security

import com.assignment.ai_service.config.security.jwt.JwtFilter
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userAuthDetailsService: UserAuthDetailsService,
    private val jwtFilter: JwtFilter
) {

    @Bean
    @Throws(Exception::class)
    fun configure(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .authorizeHttpRequests { requests ->
                requests.requestMatchers(*AUTH_WHITELIST)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling(exceptionHandlingCustomizer())
            .userDetailsService(userAuthDetailsService)
            .build()
    }

    private fun exceptionHandlingCustomizer(): Customizer<ExceptionHandlingConfigurer<HttpSecurity>> {
        return Customizer { securityExceptionHandler ->
            securityExceptionHandler.authenticationEntryPoint { _, response, _ ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
            }
            securityExceptionHandler.accessDeniedHandler { _, response, _ ->
                response.sendError(HttpServletResponse.SC_FORBIDDEN)
            }
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    companion object {
        private val AUTH_WHITELIST = arrayOf(
            "/members/signup", "/members/login"
        )
    }
}
