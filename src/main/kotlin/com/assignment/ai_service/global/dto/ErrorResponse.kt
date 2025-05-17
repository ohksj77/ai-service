package com.assignment.ai_service.global.dto

import java.util.*

class ErrorResponse(
    val code: Int,
    val message: String,
    val data: ErrorResponseData = ErrorResponseData.create(),
) {
    companion object {
        data class ErrorResponseData(
            val errorId: UUID
        ) {
            companion object {
                fun create() = ErrorResponseData(UUID.randomUUID())
            }
        }
    }
}
