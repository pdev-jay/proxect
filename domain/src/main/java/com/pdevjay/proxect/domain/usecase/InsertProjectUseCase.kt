package com.pdevjay.proxect.domain.usecase

import com.pdevjay.proxect.domain.model.Project

//class InsertProjectUseCase(
//    private val repo: ProjectRepository
//) {
//    suspend operator fun invoke(project: Project) = repo.insertProject(project)
//}

interface InsertProjectUseCase {
    suspend operator fun invoke(project: Project)
}
