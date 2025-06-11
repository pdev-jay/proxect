package com.pdevjay.proxect.domain.usecase

data class ProjectUseCases(
    val getProjects: GetProjectsUseCase,
    val insertProject: InsertProjectUseCase,
    val deleteProject: DeleteProjectUseCase,
    val updateProject: UpdateProjectUseCase,
    val getPastProjects: GetPastProjectsUseCase,
    val getFutureProjects: GetFutureProjectsUseCase
)
