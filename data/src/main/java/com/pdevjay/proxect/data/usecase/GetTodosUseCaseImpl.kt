package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.model.Todo
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.GetTodosUseCase
import javax.inject.Inject

class GetTodosUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
) : GetTodosUseCase {
    override suspend fun invoke(projectId: String): List<Todo> {
        return repository.getTodos(projectId)
    }
}