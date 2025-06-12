package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.GetProjectsForHomeUseCase
import javax.inject.Inject

class GetProjectsForHomeUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
): GetProjectsForHomeUseCase {
    override suspend fun invoke(): List<Project> {
        return repository.getProjectsForHome()
    }
}