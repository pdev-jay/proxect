package com.pdevjay.proxect.domain.usecase

import com.pdevjay.proxect.domain.model.Project

//class GetProjectsUseCase(
//    private val repository: ProjectRepository
//) {
//    suspend operator fun invoke(): List<Project> {
//        return repository.getAllProjects()
//    }
//}

interface GetProjectsForHomeUseCase {
    suspend operator fun invoke(): List<Project>
}
