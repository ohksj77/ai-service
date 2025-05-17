package com.assignment.ai_service.config.web

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebInterceptorConfig(
    private val adminInterceptor: AdminInterceptor,
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(adminInterceptor)
            .addPathPatterns("/admin/**")
    }
}
