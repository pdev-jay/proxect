package com.pdevjay.proxect.domain.usecase

interface AddTodoUseCase {
    suspend operator fun invoke(projectId: String, title: String, isDone: Boolean)
}