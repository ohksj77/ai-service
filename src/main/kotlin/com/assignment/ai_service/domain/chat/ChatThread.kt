package com.assignment.ai_service.domain.chat

import com.assignment.ai_service.global.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany

@Entity
class ChatThread : BaseEntity() {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatThread")
    val chats: MutableList<Chat> = mutableListOf()
}
