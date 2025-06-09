package com.pdevjay.proxect.data.di

//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    @Provides
//    @Singleton
//    fun provideProjectRepository(): ProjectRepository {
//        return ProjectRepositoryImpl() // 일단 임시로 구현체 대입
//    }
//
//    @Provides
//    @Singleton
//    fun provideProjectUseCases(
//        repository: ProjectRepository
//    ): ProjectUseCases {
//        return ProjectUseCases(
//            getProjects = GetProjectsUseCase(repository),
//            addProject = InsertProjectUseCase(repository),
//        )
//    }
//
//}
