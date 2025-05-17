package com.assignment.ai_service.global.dto

class SuccessResponse<T> private constructor(
    val code: Int,
    val message: String,
    val data: T,
) {
    constructor(code: Int, data: T) : this(code, SUCCESS, data)

    companion object {
        private const val SUCCESS = "success"
    }
}
