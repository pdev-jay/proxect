package com.pdevjay.proxect.domain.repository

import com.pdevjay.proxect.domain.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    suspend fun getAllProjects(): List<Project>
    suspend fun insertProject(project: Project)
    suspend fun deleteProject(id: String)
    suspend fun updateProject(project: Project)
}
