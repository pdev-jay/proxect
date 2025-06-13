package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.GetAllProjectsUseCase
import javax.inject.Inject

class GetAllProjectsUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
) : GetAllProjectsUseCase {
    override suspend fun invoke(): List<Project> {
        return repository.getAllProjects()
    }
}