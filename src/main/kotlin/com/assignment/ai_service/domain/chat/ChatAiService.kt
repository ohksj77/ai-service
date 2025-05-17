package com.assignment.ai_service.domain.chat

import com.assignment.ai_service.domain.chat.dto.ChatAiRequest
import com.assignment.ai_service.domain.chat.dto.ChatAiResponse
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface ChatAiService {
    fun chat(request: ChatAiRequest): ChatAiResponse

    fun chatWithStream(request: ChatAiRequest, sseEmitter: SseEmitter)
}
