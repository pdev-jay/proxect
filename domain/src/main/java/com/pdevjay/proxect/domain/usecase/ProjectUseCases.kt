package com.pdevjay.proxect.domain.usecase

data class ProjectUseCases(
    val getProjectsForHome: GetProjectsForHomeUseCase,
    val getAllProjects: GetAllProjectsUseCase,
    val getProjects: GetProjectsUseCase,
    val insertProject: InsertProjectUseCase,
    val deleteProject: DeleteProjectUseCase,
    val updateProject: UpdateProjectUseCase,
    val getPastProjects: GetPastProjectsUseCase,
    val getFutureProjects: GetFutureProjectsUseCase,

    val getComments: GetCommentsUseCase,
    val addComment: AddCommentUseCase,
    val deleteComment: DeleteCommentUseCase,
    val updateComment: UpdateCommentUseCase,
)
