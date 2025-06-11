package com.pdevjay.proxect.domain.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository

//class UpdateProjectUseCase (
//    private val repository: ProjectRepository
//    ) {
//        suspend operator fun invoke(project: Project) {
//            return repository.updateProject(project = project)
//        }
//    }

interface UpdateProjectUseCase {
    suspend operator fun invoke(project: Project)
}
