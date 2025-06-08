package com.pdevjay.proxect.domain.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository

class AddProjectUseCase(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(project: Project) {
        repository.addProject(project)
    }
}
