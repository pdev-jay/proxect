package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.GetProjectsUseCase
import javax.inject.Inject

class GetProjectsUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
): GetProjectsUseCase {
    override suspend fun invoke(): List<Project> {
        return repository.getAllProjects()
    }
}