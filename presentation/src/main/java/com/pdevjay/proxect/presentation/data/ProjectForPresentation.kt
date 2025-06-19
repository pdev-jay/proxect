package com.pdevjay.proxect.presentation.data

import android.os.Parcelable
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.model.ProjectStatus
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ProjectForPresentation(
    val id: String,
    val name: String,
    val description: String,
    val startDate: Long,
    val endDate: Long,
    val color: Long = 0xFF9E9E9E,
    val status: ProjectStatus = ProjectStatus.NOT_STARTED,
    val finishedDate: Long? = null
) : Parcelable

fun ProjectForPresentation.toDomain() = Project(
    id,
    name,
    description,
    startDate,
    endDate,
    color = color,
    status = status,
    finishedDate = finishedDate
)

fun Project.toPresentation() = ProjectForPresentation(
    id,
    name,
    description,
    startDate,
    endDate,
    color = color,
    status = status,
    finishedDate = finishedDate
)