package com.pdevjay.proxect.presentation.screen.calendar

import androidx.lifecycle.ViewModel
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.presentation.screen.calendar.model.CalendarDay
import com.pdevjay.proxect.presentation.screen.calendar.model.CalendarState
import com.pdevjay.proxect.presentation.screen.calendar.model.DialogContentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(CalendarState())
    val state: StateFlow<CalendarState> = _state

    init {
        generateMonthDays(_state.value.yearMonth)
    }

    private fun generateMonthDays(yearMonth: YearMonth) {
        _state.update { it.copy(yearMonth = yearMonth) }

        val today = LocalDate.now()
        val firstDayOfMonth = yearMonth.atDay(1)
        val startOfCalendar =
            firstDayOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))

        // 무조건 6주 = 42일
        val totalDays = 42
        val days = mutableListOf<CalendarDay>()

        var current = startOfCalendar
        repeat(totalDays) {
            val isCurrentMonth = current.month == yearMonth.month
            val isToday = current == today
            days.add(CalendarDay(current, isCurrentMonth, isToday))
            current = current.plusDays(1)
        }
        _state.update { it.copy(days = days) }

    }


    fun goToNextMonth() {
        generateMonthDays(_state.value.yearMonth.plusMonths(1))
    }

    fun goToPreviousMonth() {
        generateMonthDays(_state.value.yearMonth.minusMonths(1))
    }
}

