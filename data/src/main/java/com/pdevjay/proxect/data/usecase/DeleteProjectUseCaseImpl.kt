package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.DeleteProjectUseCase
import com.pdevjay.proxect.domain.usecase.InsertProjectUseCase
import javax.inject.Inject

class DeleteProjectUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
): DeleteProjectUseCase {
    override suspend fun invoke(id: String) {
        repository.deleteProject(id)

    }
}