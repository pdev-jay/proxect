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
    @SerialName("end_date") val endDate: Long
)

fun ProjectDto.toDomain() = Project(id, name, description, startDate, endDate)
fun Project.toDto() = ProjectDto(id, name, description, startDate, endDate)
