package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.InsertProjectUseCase
import javax.inject.Inject

class InsertProjectUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
): InsertProjectUseCase {
    override suspend fun invoke(project: Project) {
        repository.insertProject(project)
    }
}