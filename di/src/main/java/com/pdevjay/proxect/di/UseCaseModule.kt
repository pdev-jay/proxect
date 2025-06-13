package com.pdevjay.proxect.di

import com.pdevjay.proxect.data.usecase.DeleteProjectUseCaseImpl
import com.pdevjay.proxect.data.usecase.GetAllProjectsUseCaseImpl
import com.pdevjay.proxect.data.usecase.GetFutureProjectsUseCaseImpl
import com.pdevjay.proxect.data.usecase.GetPastProjectsUseCaseImpl
import com.pdevjay.proxect.data.usecase.GetProjectsForHomeUseCaseImpl
import com.pdevjay.proxect.data.usecase.GetProjectsUseCaseImpl
import com.pdevjay.proxect.data.usecase.InsertProjectUseCaseImpl
import com.pdevjay.proxect.data.usecase.UpdateProjectUseCaseImpl
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.DeleteProjectUseCase
import com.pdevjay.proxect.domain.usecase.GetAllProjectsUseCase
import com.pdevjay.proxect.domain.usecase.GetFutureProjectsUseCase
import com.pdevjay.proxect.domain.usecase.GetPastProjectsUseCase
import com.pdevjay.proxect.domain.usecase.GetProjectsForHomeUseCase
import com.pdevjay.proxect.domain.usecase.GetProjectsUseCase
import com.pdevjay.proxect.domain.usecase.InsertProjectUseCase
import com.pdevjay.proxect.domain.usecase.ProjectUseCases
import com.pdevjay.proxect.domain.usecase.UpdateProjectUseCase
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
    fun provideProjectUseCases(
        getProjectsForHome: GetProjectsForHomeUseCase,
        getProjects: GetProjectsUseCase,
        insertProject: InsertProjectUseCase,
        deleteProject: DeleteProjectUseCase,
        updateProject: UpdateProjectUseCase,
        getPastProjects: GetPastProjectsUseCase,
        getFutureProjects: GetFutureProjectsUseCase,
        getAllProjects: GetAllProjectsUseCase
    ): ProjectUseCases {
        return ProjectUseCases(
            getProjectsForHome = getProjectsForHome,
            getProjects = getProjects,
            insertProject = insertProject,
            deleteProject = deleteProject,
            updateProject = updateProject,
            getPastProjects = getPastProjects,
            getFutureProjects = getFutureProjects,
            getAllProjects = getAllProjects
        )
    }

}
