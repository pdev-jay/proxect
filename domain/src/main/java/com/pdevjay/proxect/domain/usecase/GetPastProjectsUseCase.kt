package com.pdevjay.proxect.domain.usecase

import com.pdevjay.proxect.domain.model.Project
import java.time.LocalDate

interface GetPastProjectsUseCase {
    suspend operator fun invoke(before: LocalDate, projectId: String? = null, limit: Int = 10): List<Project>
}