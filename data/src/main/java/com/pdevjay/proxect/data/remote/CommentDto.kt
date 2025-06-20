package com.pdevjay.proxect.data.remote

import com.pdevjay.proxect.domain.model.Comment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class CommentDto(
    val id: String? = null,
    @SerialName("project_id") val projectId: String,
    val content: String,
    val author: String? = null,
    @SerialName("created_at") val createdAt: String? = null
)


fun CommentDto.toDomain(): Comment = Comment(
    id = id ?: "", // 또는 throw if null
    projectId = projectId,
    content = content,
    author = author,
    createdAt = createdAt?.let { Instant.parse(it) } ?: Instant.EPOCH
)

fun Comment.toDto(): CommentDto = CommentDto(
    id = id,
    projectId = projectId,
    content = content,
    author = author,
    createdAt = createdAt.toString()
)
