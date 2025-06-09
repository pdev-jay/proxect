package com.pdevjay.proxect.presentation.screen.calendar.model

import java.time.LocalDate

data class CalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val isToday: Boolean
)
