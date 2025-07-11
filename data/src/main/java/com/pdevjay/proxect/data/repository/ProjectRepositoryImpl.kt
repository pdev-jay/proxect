package com.pdevjay.proxect.data.repository

import com.pdevjay.proxect.data.remote.CommentDto
import com.pdevjay.proxect.data.remote.ProjectDto
import com.pdevjay.proxect.data.remote.TodoDto
import com.pdevjay.proxect.data.remote.toDomain
import com.pdevjay.proxect.data.remote.toDto
import com.pdevjay.proxect.domain.model.Comment
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.model.ProjectStatus
import com.pdevjay.proxect.domain.model.Todo
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.utils.toEpochMillis
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient
) : ProjectRepository {

    override suspend fun getAllProjects(): List<Project> {
        return supabase.from("projects").select().decodeList<ProjectDto>().map { it.toDomain() }
    }

    override suspend fun getProjects(firstDate: LocalDate, lastDate: LocalDate): List<Project> {

        val firstDateMillis = firstDate.toEpochMillis()
        val lastDateMillis = lastDate.toEpochMillis()

        return supabase.from("projects").select {
            filter {
                // 프로젝트가 해당 달과 겹치는 조건
                gte("end_date", firstDateMillis) // 프로젝트가 달 시작 이후까지 살아있음
                lte("start_date", lastDateMillis) // 프로젝트가 달 끝 이전에 시작됨
            }
        }.decodeList<ProjectDto>().map { it.toDomain() }
    }


    override suspend fun getProjectsForHome(): List<Project> {
        val today = LocalDate.now(ZoneOffset.UTC) // 현재 UTC 날짜
        val sevenDaysAgo = today.minusDays(7)
        val sevenDaysLater = today.plusDays(7)

        // 밀리초로 변환
        val todayMillis = today.toEpochMillis()
        val yesterdayMillis = today.minusDays(1).toEpochMillis()
        val tomorrowMillis = today.plusDays(1).toEpochMillis()
        val sevenDaysAgoMillis = sevenDaysAgo.toEpochMillis()
        val sevenDaysLaterMillis = sevenDaysLater.toEpochMillis()

        return supabase.from("projects").select {
            filter {
                // 전체 조건을 포괄하는 범위: 종료일이 지난 1주일 전부터, 시작일이 1주일 뒤까지
                or {
                    and {
                        gte("end_date", todayMillis)
                        lte("start_date", todayMillis)
                    }
                    and {
                        gte("end_date", sevenDaysAgoMillis)
                        lte("end_date", yesterdayMillis)
                    }
                    and {
                        gte("start_date", tomorrowMillis)
                        lte("start_date", sevenDaysLaterMillis)
                    }
                }
            }
        }.decodeList<ProjectDto>().map { it.toDomain() }

    }

    override suspend fun insertProject(project: Project) {
        val dto = project.toDto()
        supabase.from("projects").insert(dto)
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

    override suspend fun getPastProjects(
        before: LocalDate, projectId: String?, limit: Int
    ): List<Project> {
        return supabase.from("projects").select {
            filter {
                if (projectId != null) {
                    or {
                        lt(
                            "start_date", before.toEpochMillis()
                        ) // lastLoadedProjectId의 날짜 다음 날짜
                        and {
                            eq(
                                "start_date", before.toEpochMillis()
                            ) // lastLoadedProjectId와 동일한 날짜
                            lt("id", projectId) // 해당 날짜 내에서 ID가 더 큰 프로젝트
                        }
                    }

                } else {
                    lte("start_date", before.toEpochMillis())
                }
            }

            order(column = "start_date", order = Order.DESCENDING)
            order(column = "id", order = Order.DESCENDING)
            limit(count = limit.toLong())
        }.decodeList<ProjectDto>().map { it.toDomain() }
    }

    override suspend fun getFutureProjects(
        after: LocalDate, projectId: String?, limit: Int
    ): List<Project> {
        return supabase.from("projects").select {
            filter {
                if (projectId != null) {
                    or {
                        gt("start_date", after.toEpochMillis()) // lastLoadedProjectId의 날짜 다음 날짜
                        and {
                            eq(
                                "start_date", after.toEpochMillis()
                            ) // lastLoadedProjectId와 동일한 날짜
                            gt("id", projectId) // 해당 날짜 내에서 ID가 더 큰 프로젝트
                        }
                    }

                } else {
                    gte("start_date", after.toEpochMillis())
                }
            }

            order(column = "start_date", order = Order.ASCENDING)
            order(column = "id", order = Order.ASCENDING)
            limit(count = limit.toLong())
        }.decodeList<ProjectDto>().map { it.toDomain() }
    }

    override suspend fun getComments(projectId: String): List<Comment> {
        return supabase.from("comments").select {
            filter {
                eq("project_id", projectId)
            }
        }.decodeList<CommentDto>().map { it.toDomain() }
    }

    override suspend fun addComment(projectId: String, content: String) {
        supabase.from("comments").insert(
            CommentDto(
                projectId = projectId, content = content
            )
        )
    }

    override suspend fun deleteComment(projectId: String, commentId: String) {
        supabase.from("comments").delete {
            filter {
                eq("id", commentId)
                eq("project_id", projectId)
            }
        }
    }

    override suspend fun updateComment(projectId: String, commentId: String, content: String) {
        supabase.from("comments").update({
            set("content", content)
        }) {
            filter {
                eq("id", commentId)
                eq("project_id", projectId)
            }
        }

    }

    override suspend fun getTodos(projectId: String): List<Todo> {
        return supabase.from("todos").select {
            filter {
                eq("project_id", projectId)
            }
        }.decodeList<TodoDto>().map { it.toDomain() }
    }

    override suspend fun addTodo(projectId: String, title: String, isDone: Boolean) {
        supabase.from("todos").insert(
            TodoDto(
                projectId = projectId, title = title, isDone = isDone
            )
        )
    }

    override suspend fun deleteTodo(todoId: String, projectId: String) {
        supabase.from("todos").delete {
            filter {
                eq("id", todoId)
                eq("project_id", projectId)
            }
        }
    }

    override suspend fun updateTodo(
        projectId: String, todoId: String, title: String, isDone: Boolean
    ) {
        supabase.from("todos").update({
            set("title", title)
            set("is_done", isDone)
        }) {
            filter {
                eq("id", todoId)
                eq("project_id", projectId)
            }
        }
    }

    override suspend fun searchProjects(
        query: String,
        isStatusFilterActive: Boolean,
        status: ProjectStatus?,
        isDateFilterActive: Boolean,
        startDate: Long?,
        endDate: Long?
    ): List<Project>? {

        val isFilterMeaningful =
            (query.isNotBlank() && query.length > 2) || isStatusFilterActive || isDateFilterActive

        if (!isFilterMeaningful) {
            return null
        }

        return supabase.from("projects").select {
            filter {
                if (query.isNotBlank() && query.length > 2) {
                    ilike("name", "%$query%")
                }

                if (isStatusFilterActive && status != null) {
                    eq("status", status.code)
                }

                if (isDateFilterActive && startDate != null && endDate != null) {
                    lte("start_date", endDate)
                    gte("end_date", startDate)
                }
            }
        }.decodeList<ProjectDto>().map { it.toDomain() }
    }

}

