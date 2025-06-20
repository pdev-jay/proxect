package com.pdevjay.proxect.presentation.data

import com.pdevjay.proxect.domain.model.Todo
import java.time.Instant

data class TodoForPresentation(
    val id: String? = null,
    val projectId: String,
    val title: String,
    val isDone: Boolean = false,
    val createdAt: String? = null // ISO 8601 (e.g., "2025-06-20T08:00:00Z")
)

fun TodoForPresentation.toDomain(): Todo = Todo(
    id = id ?: "",
    projectId = projectId,
    title = title,
    isDone = isDone,
    createdAt = createdAt?.let { Instant.parse(it) } ?: Instant.EPOCH
)

fun Todo.toPresentation() = TodoForPresentation(
    id,
    projectId,
    title,
    isDone,
    createdAt.toString()
)