package com.assignment.ai_service.domain.chat.dto

data class ChatAiRequest(
    val model: String,
    val messages: List<Message>,
    val stream: Boolean = false,
) {
    data class Message(
        val role: String,
        val content: String
    )
}
