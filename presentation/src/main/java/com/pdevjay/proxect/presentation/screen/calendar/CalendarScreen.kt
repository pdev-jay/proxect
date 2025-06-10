package com.pdevjay.proxect.presentation.screen.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.presentation.screen.add.ProjectViewModel
import com.pdevjay.proxect.presentation.screen.common.ProjectDialog
import com.pdevjay.proxect.presentation.screen.calendar.component.CalendarTopBar
import com.pdevjay.proxect.presentation.screen.calendar.component.CalendarWeekGrid
import com.pdevjay.proxect.presentation.screen.calendar.model.CalendarDay

@Composable
fun CalendarScreen(calendarViewModel: CalendarViewModel = hiltViewModel(), projectViewModel: ProjectViewModel = hiltViewModel()) {
    val calendarState by calendarViewModel.state.collectAsState()
    val projects by projectViewModel.projects.collectAsState()

    var calendarDay by remember { mutableStateOf<CalendarDay?>(null) }
    var dayProjects by remember { mutableStateOf<List<Project>>(emptyList()) }
    var isModalVisible by remember { mutableStateOf(false) }

    val padding = PaddingValues(4.dp)

    if (isModalVisible && calendarDay != null){
        ProjectDialog(
            selectedDate = calendarDay!!.date,
            projects = dayProjects,
        ) {
            isModalVisible = false
        }
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ){
        CalendarTopBar(padding, calendarViewModel, calendarState)
        CalendarWeekGrid(calendarState, projects, onDayClick = { day, projects ->
            calendarDay = day
            dayProjects = projects
            isModalVisible = true
        })
    }
}


