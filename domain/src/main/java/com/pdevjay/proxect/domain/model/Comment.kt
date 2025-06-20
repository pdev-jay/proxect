package com.pdevjay.proxect.domain.model

import java.time.Instant


data class Comment(
    val id: String,
    val projectId: String,
    val content: String,
    val author: String?,
    val createdAt: Instant
)
