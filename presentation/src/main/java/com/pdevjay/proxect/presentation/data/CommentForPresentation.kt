package com.pdevjay.proxect.presentation.data

import com.pdevjay.proxect.domain.model.Comment
import java.time.Instant

data class CommentForPresentation(
    val id: String? = null,
    val projectId: String,
    val content: String,
    val author: String? = null,
    val createdAt: Instant? = null
)

fun Comment.toPresentation() = CommentForPresentation(
    id,
    projectId,
    content,
    author,
    createdAt
)

fun CommentForPresentation.toDomain() = Comment(
    id = id ?: "",
    projectId,
    content,
    author,
    createdAt ?: Instant.EPOCH
)
