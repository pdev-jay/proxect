package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.model.ProjectStatus
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.SearchProjectsUseCase
import javax.inject.Inject

class SearchProjectsUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
) : SearchProjectsUseCase {
    override suspend fun invoke(
        query: String,
        isStatusFilterActive: Boolean,
        status: ProjectStatus?,
        isDateFilterActive: Boolean,
        startDate: Long?,
        endDate: Long?
    ): List<Project>? {
        return repository.searchProjects(
            query = query,
            isStatusFilterActive = isStatusFilterActive,
            status = status,
            isDateFilterActive = isDateFilterActive,
            startDate = startDate,
            endDate = endDate
        )
    }

}