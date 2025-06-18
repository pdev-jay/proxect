package com.pdevjay.proxect.presentation.screen.calendar.model

import java.time.YearMonth

enum class DialogContentType {
    ProjectList, ProjectDetail, EditProject, AddProject
}

data class CalendarState(
    val yearMonth: YearMonth = YearMonth.now(),
    val days: List<CalendarDay> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
