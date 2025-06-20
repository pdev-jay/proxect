package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.AddTodoUseCase
import javax.inject.Inject

class AddTodoUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
) : AddTodoUseCase {
    override suspend fun invoke(projectId: String, title: String, isDone: Boolean) {
        repository.addTodo(projectId, title, isDone)
    }
}