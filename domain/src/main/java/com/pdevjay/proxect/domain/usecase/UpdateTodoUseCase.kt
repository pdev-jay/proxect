package com.pdevjay.proxect.domain.usecase

interface UpdateTodoUseCase {
    suspend operator fun invoke(projectId: String, todoId: String, title: String, isDone: Boolean)
}