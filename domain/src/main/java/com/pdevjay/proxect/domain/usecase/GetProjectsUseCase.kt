package com.pdevjay.proxect.domain.usecase

import com.pdevjay.proxect.domain.model.Project
import java.time.LocalDate

interface GetProjectsUseCase {
    suspend operator fun invoke(firstDate: LocalDate, lastDate: LocalDate): List<Project>
}
