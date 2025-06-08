package com.pdevjay.proxect.data.di

import com.pdevjay.proxect.data.repository.FakeProjectRepository
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.AddProjectUseCase
import com.pdevjay.proxect.domain.usecase.DeleteProjectUseCase
import com.pdevjay.proxect.domain.usecase.GetProjectsUseCase
import com.pdevjay.proxect.domain.usecase.ProjectUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideProjectRepository(): ProjectRepository {
        return FakeProjectRepository() // 일단 임시로 구현체 대입
    }

    @Provides
    @Singleton
    fun provideProjectUseCases(
        repository: ProjectRepository
    ): ProjectUseCases {
        return ProjectUseCases(
            getProjects = GetProjectsUseCase(repository),
            addProject = AddProjectUseCase(repository),
            deleteProject = DeleteProjectUseCase(repository)
        )
    }

}
