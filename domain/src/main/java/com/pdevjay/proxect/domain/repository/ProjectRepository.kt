package com.pdevjay.proxect.domain.repository

import com.pdevjay.proxect.domain.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun getAllProjects(): Flow<List<Project>>
    suspend fun addProject(project: Project)
    suspend fun deleteProject(id: String)
}
