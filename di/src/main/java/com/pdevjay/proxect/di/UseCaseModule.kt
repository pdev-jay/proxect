package com.pdevjay.proxect.di

import com.pdevjay.proxect.data.usecase.AddCommentUseCaseImpl
import com.pdevjay.proxect.data.usecase.AddTodoUseCaseImpl
import com.pdevjay.proxect.data.usecase.DeleteCommentUseCaseImpl
import com.pdevjay.proxect.data.usecase.DeleteProjectUseCaseImpl
import com.pdevjay.proxect.data.usecase.DeleteTodoUseCaseImpl
import com.pdevjay.proxect.data.usecase.GetAllProjectsUseCaseImpl
import com.pdevjay.proxect.data.usecase.GetCommentsUseCaseImpl
import com.pdevjay.proxect.data.usecase.GetFutureProjectsUseCaseImpl
import com.pdevjay.proxect.data.usecase.GetPastProjectsUseCaseImpl
import com.pdevjay.proxect.data.usecase.GetProjectsForHomeUseCaseImpl
import com.pdevjay.proxect.data.usecase.GetProjectsUseCaseImpl
import com.pdevjay.proxect.data.usecase.GetTodosUseCaseImpl
import com.pdevjay.proxect.data.usecase.InsertProjectUseCaseImpl
import com.pdevjay.proxect.data.usecase.SearchProjectsUseCaseImpl
import com.pdevjay.proxect.data.usecase.UpdateCommentUseCaseImpl
import com.pdevjay.proxect.data.usecase.UpdateProjectUseCaseImpl
import com.pdevjay.proxect.data.usecase.UpdateTodoUseCaseImpl
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.AddCommentUseCase
import com.pdevjay.proxect.domain.usecase.AddTodoUseCase
import com.pdevjay.proxect.domain.usecase.DeleteCommentUseCase
import com.pdevjay.proxect.domain.usecase.DeleteProjectUseCase
import com.pdevjay.proxect.domain.usecase.DeleteTodoUseCase
import com.pdevjay.proxect.domain.usecase.GetAllProjectsUseCase
import com.pdevjay.proxect.domain.usecase.GetCommentsUseCase
import com.pdevjay.proxect.domain.usecase.GetFutureProjectsUseCase
import com.pdevjay.proxect.domain.usecase.GetPastProjectsUseCase
import com.pdevjay.proxect.domain.usecase.GetProjectsForHomeUseCase
import com.pdevjay.proxect.domain.usecase.GetProjectsUseCase
import com.pdevjay.proxect.domain.usecase.GetTodosUseCase
import com.pdevjay.proxect.domain.usecase.InsertProjectUseCase
import com.pdevjay.proxect.domain.usecase.ProjectUseCases
import com.pdevjay.proxect.domain.usecase.SearchProjectsUseCase
import com.pdevjay.proxect.domain.usecase.UpdateCommentUseCase
import com.pdevjay.proxect.domain.usecase.UpdateProjectUseCase
import com.pdevjay.proxect.domain.usecase.UpdateTodoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetProjectsForHomeUseCase(
        repo: ProjectRepository
    ): GetProjectsForHomeUseCase = GetProjectsForHomeUseCaseImpl(repo)

    @Provides
    fun provideGetProjectsUseCase(
        repo: ProjectRepository
    ): GetProjectsUseCase = GetProjectsUseCaseImpl(repo)

    @Provides
    fun provideInsertProjectUseCase(
        repo: ProjectRepository
    ): InsertProjectUseCase = InsertProjectUseCaseImpl(repo)

    @Provides
    fun provideDeleteProjectUseCase(
        repo: ProjectRepository
    ): DeleteProjectUseCase = DeleteProjectUseCaseImpl(repo)

    @Provides
    fun provideUpdateProjectUseCase(
        repo: ProjectRepository
    ): UpdateProjectUseCase = UpdateProjectUseCaseImpl(repo)

    @Provides
    fun provideGetPastProjectsUseCase(
        repo: ProjectRepository
    ): GetPastProjectsUseCase = GetPastProjectsUseCaseImpl(repo)

    @Provides
    fun provideGetFutureProjectsUseCase(
        repo: ProjectRepository
    ): GetFutureProjectsUseCase = GetFutureProjectsUseCaseImpl(repo)

    @Provides
    fun provideGetAllProjectsUseCase(
        repo: ProjectRepository
    ): GetAllProjectsUseCase = GetAllProjectsUseCaseImpl(repo)

    @Provides
    fun provideAddCommentUseCase(
        repo: ProjectRepository
    ): AddCommentUseCase = AddCommentUseCaseImpl(repo)

    @Provides
    fun provideGetCommentsUseCase(
        repo: ProjectRepository
    ): GetCommentsUseCase = GetCommentsUseCaseImpl(repo)

    @Provides
    fun provideDeleteCommentUseCase(
        repo: ProjectRepository
    ): DeleteCommentUseCase = DeleteCommentUseCaseImpl(repo)

    @Provides
    fun provideUpdateCommentUseCase(
        repo: ProjectRepository
    ): UpdateCommentUseCase = UpdateCommentUseCaseImpl(repo)

    @Provides
    fun provideGetTodosUseCase(
        repo: ProjectRepository
    ): GetTodosUseCase = GetTodosUseCaseImpl(repo)

    @Provides
    fun provideAddTodoUseCase(
        repo: ProjectRepository
    ): AddTodoUseCase = AddTodoUseCaseImpl(repo)

    @Provides
    fun provideDeleteTodoUseCase(
        repo: ProjectRepository
    ): DeleteTodoUseCase = DeleteTodoUseCaseImpl(repo)

    @Provides
    fun provideUpdateTodoUseCase(
        repo: ProjectRepository
    ): UpdateTodoUseCase = UpdateTodoUseCaseImpl(repo)

    @Provides
    fun provideSearchProjectsUseCase(
        repo: ProjectRepository
    ): SearchProjectsUseCase = SearchProjectsUseCaseImpl(repo)

    @Provides
    fun provideProjectUseCases(
        getProjectsForHome: GetProjectsForHomeUseCase,
        getProjects: GetProjectsUseCase,
        insertProject: InsertProjectUseCase,
        deleteProject: DeleteProjectUseCase,
        updateProject: UpdateProjectUseCase,
        getPastProjects: GetPastProjectsUseCase,
        getFutureProjects: GetFutureProjectsUseCase,
        getAllProjects: GetAllProjectsUseCase,
        addComment: AddCommentUseCase,
        getComments: GetCommentsUseCase,
        deleteComment: DeleteCommentUseCase,
        updateComment: UpdateCommentUseCase,
        getTodos: GetTodosUseCase,
        addTodo: AddTodoUseCase,
        deleteTodo: DeleteTodoUseCase,
        updateTodo: UpdateTodoUseCase,
        searchProjects: SearchProjectsUseCase
    ): ProjectUseCases {
        return ProjectUseCases(
            getProjectsForHome = getProjectsForHome,
            getProjects = getProjects,
            insertProject = insertProject,
            deleteProject = deleteProject,
            updateProject = updateProject,
            getPastProjects = getPastProjects,
            getFutureProjects = getFutureProjects,
            getAllProjects = getAllProjects,
            addComment = addComment,
            getComments = getComments,
            deleteComment = deleteComment,
            updateComment = updateComment,
            getTodos = getTodos,
            addTodo = addTodo,
            deleteTodo = deleteTodo,
            updateTodo = updateTodo,
            searchProjects = searchProjects
        )
    }

}
