package com.pdevjay.proxect.data.di

import com.pdevjay.proxect.data.repository.ProjectRepositoryImpl
import com.pdevjay.proxect.domain.repository.ProjectRepository
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
}