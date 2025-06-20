package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.UpdateTodoUseCase
import javax.inject.Inject

class UpdateTodoUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
) : UpdateTodoUseCase {
    override suspend fun invoke(projectId: String, todoId: String, title: String, isDone: Boolean) {
        repository.updateTodo(projectId, todoId, title, isDone)
    }
}