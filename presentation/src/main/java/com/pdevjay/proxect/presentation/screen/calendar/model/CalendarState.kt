package com.pdevjay.proxect.presentation.screen.calendar.model

import com.pdevjay.proxect.domain.model.Project
import java.time.LocalDate
import java.time.YearMonth

enum class DialogContentType {
    ProjectList, ProjectDetail
}

data class CalendarState(
    val yearMonth: YearMonth = YearMonth.now(),
    val days: List<CalendarDay> = emptyList(),
    val projects: List<Project> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
