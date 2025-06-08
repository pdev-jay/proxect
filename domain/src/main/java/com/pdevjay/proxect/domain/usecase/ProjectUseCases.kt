package com.pdevjay.proxect.domain.usecase

data class ProjectUseCases(
    val getProjects: GetProjectsUseCase,
    val addProject: AddProjectUseCase,
    val deleteProject: DeleteProjectUseCase
)
