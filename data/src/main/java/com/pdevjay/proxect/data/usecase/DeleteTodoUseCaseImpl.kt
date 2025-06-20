package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.DeleteTodoUseCase
import javax.inject.Inject

class DeleteTodoUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
) :
    DeleteTodoUseCase {
    override suspend fun invoke(todoId: String, projectId: String) {
        repository.deleteTodo(todoId, projectId)
    }
}