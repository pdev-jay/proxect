package com.pdevjay.proxect.data.repository

import com.pdevjay.proxect.data.remote.ProjectDto
import com.pdevjay.proxect.data.remote.toDomain
import com.pdevjay.proxect.data.remote.toDto
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.utils.toEpochMillis
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import java.time.LocalDate
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

    override suspend fun getPastProjects(before: LocalDate, limit: Int): List<Project> {
        return supabase
            .from("projects")
            .select() {
                filter {
                    lt("start_date", before.toEpochMillis())
                }

                order(column = "start_date", order = Order.ASCENDING)

                limit(count = limit.toLong())
            }
            .decodeList<ProjectDto>()
            .map { it.toDomain() }
    }

    override suspend fun getFutureProjects(after: LocalDate, limit: Int): List<Project> {
        return supabase
            .from("projects")
            .select() {
                filter {
                    gt("start_date", after.toEpochMillis())
                }

                order(column = "start_date", order = Order.ASCENDING)

                limit(count = limit.toLong())
            }
            .decodeList<ProjectDto>()
            .map { it.toDomain() }
    }


}

