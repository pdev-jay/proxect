package com.pdevjay.proxect.domain.repository

import com.pdevjay.proxect.domain.model.Project
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ProjectRepository {
    suspend fun getAllProjects(): List<Project>
    suspend fun getProjects(firstDate: LocalDate, lastDate: LocalDate): List<Project>
    suspend fun getProjectsForHome(): List<Project>
    suspend fun insertProject(project: Project)
    suspend fun deleteProject(id: String)
    suspend fun updateProject(project: Project)
    suspend fun getPastProjects(before: LocalDate, projectId: String? = null, limit: Int = 10): List<Project>
    suspend fun getFutureProjects(after: LocalDate, projectId: String? = null, limit: Int = 10): List<Project>
}
