package com.pdevjay.proxect.domain.model

import java.time.Instant


data class Todo(
    val id: String,
    val projectId: String,
    val title: String,
    val isDone: Boolean,
    val createdAt: Instant
)
