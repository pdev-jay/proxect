package com.pdevjay.proxect.domain.usecase

interface DeleteTodoUseCase {
    suspend operator fun invoke(todoId: String, projectId: String)
}