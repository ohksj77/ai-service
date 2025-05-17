package com.assignment.ai_service.global

import com.assignment.ai_service.global.dto.SuccessResponse
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@RestControllerAdvice
class ResponseWrapper : ResponseBodyAdvice<Any?> {
    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>,
    ): Boolean = returnType.getParameterType() != String::class.java

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): Any? {
        val statusCode = returnType
            .getMethodAnnotation(ResponseStatus::class.java)
            ?.code
            ?.value()
            ?: HttpStatus.OK.value()

        if (statusCode.isError()) {
            return body
        }
        return SuccessResponse(statusCode, body)
    }

    private fun Int.isError() = this >= 400
}
