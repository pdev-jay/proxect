package com.pdevjay.proxect.domain.usecase

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository

//class DeleteProjectUseCase(
//    private val repository: ProjectRepository
//) {
//    suspend operator fun invoke(id: String) {
//        repository.deleteProject(id)
//    }
//}

interface DeleteProjectUseCase {
    suspend operator fun invoke(id: String)
}
