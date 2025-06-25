package com.pdevjay.proxect.domain.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.model.ProjectStatus

interface SearchProjectsUseCase {
    suspend operator fun invoke(
        query: String,
        isStatusFilterActive: Boolean,
        status: ProjectStatus?,
        isDateFilterActive: Boolean,
        startDate: Long?,
        endDate: Long?
    ): List<Project>?
}