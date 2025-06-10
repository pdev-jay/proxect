package com.pdevjay.proxect.data.di

import com.pdevjay.proxect.data.repository.ProjectRepositoryImpl
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.DeleteProjectUseCase
import com.pdevjay.proxect.domain.usecase.GetProjectsUseCase
import com.pdevjay.proxect.domain.usecase.InsertProjectUseCase
import com.pdevjay.proxect.domain.usecase.ProjectUseCases
import com.pdevjay.proxect.domain.usecase.UpdateProjectUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideProjectRepository(
        supabase: SupabaseClient
    ): ProjectRepository = ProjectRepositoryImpl(supabase)

    @Provides
    @Singleton
    fun provideProjectUseCases(
        repository: ProjectRepository
    ): ProjectUseCases {
        return ProjectUseCases(
            getProjects = GetProjectsUseCase(repository),
            insertProject = InsertProjectUseCase(repository),
            deleteProject = DeleteProjectUseCase(repository),
            updateProject = UpdateProjectUseCase(repository)
        )
    }
}
