package com.assignment.ai_service.domain.chat.dto

import jakarta.validation.constraints.NotBlank

data class ChatRequest(
    @field:NotBlank
    var question: String? = null,
    var model: String? = null,
) {
    fun toChatAiRequest(): ChatAiRequest {
        return ChatAiRequest(
            model = model ?: "gpt-3.5-turbo",
            messages = listOf(ChatAiRequest.Message("user", question ?: "")),
        )
    }

    fun toStreamChatAiRequest(): ChatAiRequest {
        return ChatAiRequest(
            model = model ?: "gpt-3.5-turbo",
            messages = listOf(ChatAiRequest.Message("user", question ?: "")),
            stream = true,
        )
    }
}
