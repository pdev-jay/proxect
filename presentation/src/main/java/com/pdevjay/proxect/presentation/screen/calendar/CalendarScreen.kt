package com.pdevjay.proxect.presentation.screen.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdevjay.proxect.presentation.screen.add.ProjectViewModel
import com.pdevjay.proxect.presentation.screen.calendar.component.CalendarDialog
import com.pdevjay.proxect.presentation.screen.calendar.component.CalendarGrid
import com.pdevjay.proxect.presentation.screen.calendar.component.CalendarTopBar
import com.pdevjay.proxect.presentation.screen.calendar.component.CalendarWeekGrid

@Composable
fun CalendarScreen(viewModel: CalendarViewModel = hiltViewModel(), projectViewModel: ProjectViewModel = hiltViewModel()) {
    val calendarState by viewModel.state.collectAsState()
    val projects by projectViewModel.projects.collectAsState()

    val padding = PaddingValues(4.dp)
    if (calendarState.isModalVisible && calendarState.selectedDate != null) {
        CalendarDialog(viewModel, calendarState)
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ){
        CalendarTopBar(padding, viewModel, calendarState)
        CalendarWeekGrid(calendarState, projects, onDayClick = { calendarDay ->
            viewModel.selectDate(calendarDay.date)
        })
    }
}


