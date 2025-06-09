package com.pdevjay.proxect.data.repository

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class FakeProjectRepository @Inject constructor() : ProjectRepository {
//
//    private val projects = MutableStateFlow<List<Project>>(emptyList())
//
//    override fun getAllProjects(): Flow<List<Project>> = projects.asStateFlow()
//
//    override suspend fun addProject(project: Project) {
//        projects.value += project
//    }
//
//    override suspend fun deleteProject(id: String) {
//        projects.value = projects.value.filterNot { it.id == id }
//    }
//}
