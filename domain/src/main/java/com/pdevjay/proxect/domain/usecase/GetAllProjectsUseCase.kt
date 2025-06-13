package com.pdevjay.proxect.domain.usecase

import com.pdevjay.proxect.domain.model.Project
import java.time.LocalDate

interface GetAllProjectsUseCase {
    suspend operator fun invoke(): List<Project>
}