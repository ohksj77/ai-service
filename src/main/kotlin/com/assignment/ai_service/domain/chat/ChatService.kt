package com.assignment.ai_service.domain.chat

import com.assignment.ai_service.domain.chat.dto.ChatRequest
import com.assignment.ai_service.domain.chat.dto.ChatResultResponse
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Service
class ChatService(
    private val chatRepository: ChatRepository,
    private val chatAiService: ChatAiService
) {
    private val sseEmitters: MutableMap<Long, SseEmitter> = mutableMapOf()

    fun chat(request: ChatRequest, memberId: Long): ChatResultResponse {
        chatAiService.chat(request.toChatAiRequest())
        TODO("Implement chat feature")
    }

    fun chatWithStream(request: ChatRequest, memberId: Long): SseEmitter {
        val sseEmitter = SseEmitter()
        sseEmitters[memberId] = sseEmitter
        chatAiService.chatWithStream(request.toChatAiRequest(), sseEmitter)
        return sseEmitter
    }
}
