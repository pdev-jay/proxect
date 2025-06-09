package com.pdevjay.proxect.domain.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository

class InsertProjectUseCase(
    private val repo: ProjectRepository
) {
    suspend operator fun invoke(project: Project) = repo.insertProject(project)
}
