package com.pdevjay.proxect.data.repository

import com.pdevjay.proxect.data.remote.ProjectDto
import com.pdevjay.proxect.data.remote.toDomain
import com.pdevjay.proxect.data.remote.toDto
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient
) : ProjectRepository {

    override suspend fun getAllProjects(): List<Project> {
        return supabase
            .postgrest["projects"]
            .select()
            .decodeList<ProjectDto>()
            .map { it.toDomain() }
    }

    override suspend fun insertProject(project: Project) {
        val dto = project.toDto()
        supabase
            .postgrest["projects"]
            .insert(dto)
    }

    override suspend fun deleteProject(id: String) {
        supabase.from("projects").delete {
            filter {
                eq("id", id)
            }
        }
    }

    override suspend fun updateProject(project: Project) {
        val dto = project.toDto()
        supabase.from("projects").upsert(dto)

    }
}
