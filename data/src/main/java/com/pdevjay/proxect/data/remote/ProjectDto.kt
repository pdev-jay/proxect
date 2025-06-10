package com.pdevjay.proxect.data.remote

import com.pdevjay.proxect.domain.model.Project
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectDto(
    val id: String,
    val name: String,
    val description: String,
    @SerialName("start_date") val startDate: Long,
    @SerialName("end_date") val endDate: Long,
    val color: Long = 0xFF888888 // 기본 회색, optional 처리
)

fun ProjectDto.toDomain() = Project(
    id, name, description, startDate, endDate, color = color
)

fun Project.toDto() = ProjectDto(
    id, name, description, startDate, endDate, color = color
)
