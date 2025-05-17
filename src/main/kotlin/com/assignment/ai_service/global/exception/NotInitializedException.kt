package com.assignment.ai_service.global.exception

class NotInitializedException(
    override val message: String,
) : IllegalStateException(message) {
    companion object {
        fun entityId() = NotInitializedException("Entity id is not initialized")
    }
}
