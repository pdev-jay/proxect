package com.pdevjay.proxect.data.remote

import com.pdevjay.proxect.domain.model.Todo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class TodoDto(
    val id: String? = null,
    @SerialName("project_id") val projectId: String,
    val title: String,
    @SerialName("is_done") val isDone: Boolean = false,
    @SerialName("created_at") val createdAt: String? = null // ISO 8601 (e.g., "2025-06-20T08:00:00Z")
)

fun TodoDto.toDomain(): Todo = Todo(
    id = id ?: "",
    projectId = projectId,
    title = title,
    isDone = isDone,
    createdAt = createdAt?.let { Instant.parse(it) } ?: Instant.EPOCH
)
