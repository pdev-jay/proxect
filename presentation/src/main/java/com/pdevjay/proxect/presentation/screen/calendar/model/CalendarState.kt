package com.pdevjay.proxect.presentation.screen.calendar.model

import java.time.LocalDate
import java.time.YearMonth


data class CalendarState(
    val yearMonth: YearMonth = YearMonth.now(),
    val days: List<CalendarDay> = emptyList(),
    val selectedDate: LocalDate? = null,
    val isModalVisible:Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
