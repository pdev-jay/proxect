package com.pdevjay.proxect.domain.usecase

import com.pdevjay.proxect.domain.model.Project
import java.time.LocalDate

interface GetFutureProjectsUseCase {
    suspend operator fun invoke(after: LocalDate, limit: Int = 10): List<Project>
}