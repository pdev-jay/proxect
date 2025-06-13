package com.pdevjay.proxect.domain.model

data class Project(
    val id: String,
    val name: String,
    val description: String,
    val startDate: Long,
    val endDate: Long,
    val color: Long = 0xFF9E9E9E,
    val status: ProjectStatus = ProjectStatus.NOT_STARTED,
    val finishedDate: Long? = null
)

enum class ProjectStatus(val code: Int, val displayName: String) {
    NOT_STARTED(0, "시작 전"),
    IN_PROGRESS(1, "진행 중"),
    ON_HOLD(2, "보류"),
    COMPLETED(3, "완료");

    companion object {
        fun fromCode(code: Int): ProjectStatus =
            entries.firstOrNull { it.code == code } ?: NOT_STARTED
    }
}
