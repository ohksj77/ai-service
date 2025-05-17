package com.assignment.ai_service.domain.chat

import com.assignment.ai_service.domain.chat.dto.ChatAiRequest
import com.assignment.ai_service.domain.chat.dto.ChatAiResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Component
class ChatAiGptService(
    @Value("\${openai.api-key}") private val apiKey: String,
    @Value("\${openai.api-url}") private val apiUrl: String,
    @Value("\${openai.api-path}") private val apiPath: String,
) : ChatAiService {

    private val restClient: RestClient = generateRestClient()

    private fun generateRestClient(): RestClient =
        RestClient.builder()
            .baseUrl(apiUrl)
            .defaultHeader(HttpHeaders.AUTHORIZATION, apiKey)
            .build()

    override fun chat(request: ChatAiRequest): ChatAiResponse {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }
        TODO("Implement chat feature with RestClient")
    }

    override fun chatWithStream(request: ChatAiRequest, sseEmitter: SseEmitter) {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            accept = listOf(MediaType.TEXT_EVENT_STREAM, MediaType.APPLICATION_JSON)
        }
        TODO("Implement chatWithStream feature with RestClient")
    }
}
