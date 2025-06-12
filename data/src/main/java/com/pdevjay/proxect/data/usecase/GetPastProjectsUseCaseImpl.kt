package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.GetPastProjectsUseCase
import java.time.LocalDate
import javax.inject.Inject

class GetPastProjectsUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
): GetPastProjectsUseCase {

    override suspend fun invoke(before: LocalDate, projectId: String?, limit: Int): List<Project> {
        return repository.getPastProjects(before, projectId, limit)
    }
}