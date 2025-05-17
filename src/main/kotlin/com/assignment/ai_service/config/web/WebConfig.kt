package com.assignment.ai_service.config.web

import com.assignment.ai_service.config.auth.AuthorizedMemberArgumentResolver
import com.assignment.ai_service.config.auth.AuthorizedMemberIdArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val authorizedMemberArgumentResolver: AuthorizedMemberArgumentResolver,
    private val authorizedMemberIdArgumentResolver: AuthorizedMemberIdArgumentResolver,
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authorizedMemberArgumentResolver)
        resolvers.add(authorizedMemberIdArgumentResolver)
    }
}
