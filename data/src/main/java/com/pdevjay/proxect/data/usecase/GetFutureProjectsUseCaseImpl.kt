package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.GetFutureProjectsUseCase
import com.pdevjay.proxect.domain.usecase.GetPastProjectsUseCase
import java.time.LocalDate
import javax.inject.Inject

class GetFutureProjectsUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
): GetFutureProjectsUseCase {

    override suspend fun invoke(after: LocalDate, limit: Int): List<Project> {
        return repository.getFutureProjects(after, limit)
    }
}