package com.pdevjay.proxect.domain.usecase

import com.pdevjay.proxect.domain.model.Todo

interface GetTodosUseCase {
    suspend operator fun invoke(projectId: String): List<Todo>
}