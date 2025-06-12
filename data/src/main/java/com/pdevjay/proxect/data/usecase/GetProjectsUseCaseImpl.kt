package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.GetProjectsUseCase
import java.time.LocalDate
import javax.inject.Inject

class GetProjectsUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
): GetProjectsUseCase {
    override suspend fun invoke(firstDate: LocalDate, lastDate: LocalDate): List<Project> {
        return repository.getProjects(firstDate, lastDate)
    }
}