package com.pdevjay.proxect.data.remote

import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.model.ProjectStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectDto(
    val id: String,
    val name: String,
    val description: String,
    @SerialName("start_date") val startDate: Long,
    @SerialName("end_date") val endDate: Long,
    val color: Long = 0xFF9E9E9E,
    @SerialName("status") val status: Int = ProjectStatus.NOT_STARTED.code,
    @SerialName("finished_date") val finishedDate: Long? = null
)

fun ProjectDto.toDomain() = Project(
    id,
    name,
    description,
    startDate,
    endDate,
    color = color,
    status = ProjectStatus.fromCode(status),
    finishedDate = finishedDate
)

fun Project.toDto() = ProjectDto(
    id,
    name,
    description,
    startDate,
    endDate,
    color = color,
    status = status.code,
    finishedDate = finishedDate
)