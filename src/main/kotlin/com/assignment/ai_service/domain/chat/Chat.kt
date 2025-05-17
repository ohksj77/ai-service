package com.assignment.ai_service.domain.chat

import com.assignment.ai_service.global.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Chat(
    var question: String,
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    var chatThread: ChatThread
) : BaseEntity() {
    var answer: String? = null
    var isAnswered: Boolean = false

    fun updateAnswer(answer: String) {
        this.answer = answer
        this.isAnswered = true
    }
}
