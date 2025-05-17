package com.assignment.ai_service.domain.chat

import com.assignment.ai_service.config.auth.AuthorizedMemberId
import com.assignment.ai_service.domain.chat.dto.ChatRequest
import com.assignment.ai_service.domain.chat.dto.ChatResultResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
@RequestMapping("/chats")
class ChatController(
    private val chatService: ChatService,
) {
    @PostMapping(params = ["isStreaming=false"])
    fun chat(@Valid @RequestBody request: ChatRequest, @AuthorizedMemberId memberId: Long): ChatResultResponse {
        return chatService.chat(request, memberId)
    }

    @PostMapping(params = ["isStreaming=true"])
    fun chatWithStream(@Valid @RequestBody request: ChatRequest, @AuthorizedMemberId memberId: Long): SseEmitter {
        return chatService.chatWithStream(request, memberId)
    }
}
