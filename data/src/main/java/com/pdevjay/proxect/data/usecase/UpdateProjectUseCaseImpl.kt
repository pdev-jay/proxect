package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.DeleteProjectUseCase
import com.pdevjay.proxect.domain.usecase.UpdateProjectUseCase
import javax.inject.Inject

class UpdateProjectUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
): UpdateProjectUseCase {
    override suspend fun invoke(project: Project) {
        repository.updateProject(project)
    }
}