package com.pdevjay.proxect.presentation.data

import com.pdevjay.proxect.domain.model.ProjectStatus
import kotlinx.serialization.Serializable

@Serializable
data class ProjectDetailNav(
    val id: String,
    val name: String,
    val description: String,
    val startDate: Long,
    val endDate: Long,
    val color: Long = 0xFF9E9E9E,
    val status: ProjectStatus = ProjectStatus.NOT_STARTED,
    val finishedDate: Long? = null
) {
    fun toPresentation() = ProjectForPresentation(
        id,
        name,
        description,
        startDate,
        endDate,
        color = color,
        status = status,
        finishedDate = finishedDate
    )
}

@Serializable
data class ProjectEditNav(
    val id: String,
    val name: String,
    val description: String,
    val startDate: Long,
    val endDate: Long,
    val color: Long = 0xFF9E9E9E,
    val status: ProjectStatus = ProjectStatus.NOT_STARTED,
    val finishedDate: Long? = null
) {
    fun toPresentation() = ProjectForPresentation(
        id,
        name,
        description,
        startDate,
        endDate,
        color = color,
        status = status,
        finishedDate = finishedDate
    )
}

@Serializable
object ProjectListNav

@Serializable
object ProjectAddNav

@Serializable
object ProjectDashboardNav

@Serializable
object ProjectCalendarNav

@Serializable
object ProjectSearchNav

@Serializable
object ProjectSettingsNav
