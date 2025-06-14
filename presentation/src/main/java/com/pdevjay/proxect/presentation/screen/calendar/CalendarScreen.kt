package com.pdevjay.proxect.presentation.screen.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdevjay.proxect.domain.utils.toUTCLocalDate
import com.pdevjay.proxect.presentation.screen.calendar.component.CalendarTopBar
import com.pdevjay.proxect.presentation.screen.calendar.component.CalendarWeekGrid
import com.pdevjay.proxect.presentation.screen.common.ProjectDialog
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel

@Composable
fun CalendarScreen(
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    projectViewModel: ProjectViewModel = hiltViewModel()
) {
    val calendarState by calendarViewModel.state.collectAsState()
    val projects by projectViewModel.projects.collectAsState()

    var isModalVisible by remember { mutableStateOf(false) }

    val padding = PaddingValues(4.dp)

    LaunchedEffect(calendarState.days) {
        projectViewModel.loadProjects(calendarState.days.first().date, calendarState.days.last().date) // month 기준 1일
    }

    LaunchedEffect(projects, calendarState.selectedDate) {
        calendarState.selectedDate?.let { date ->
            val inDate = projects.filter {
                it.startDate.toUTCLocalDate() <= date && it.endDate.toUTCLocalDate() >= date
            }
            calendarViewModel.setProjectsInDate(inDate)
        }
    }


    if (isModalVisible && calendarState.selectedDate != null) {
        ProjectDialog(
            selectedDate = calendarState.selectedDate!!,
            projects = calendarState.projects,
            onDismiss = {
                isModalVisible = false
                calendarViewModel.setSelectedDate(null)
                calendarViewModel.setProjectsInDate(emptyList())
            },
            onDelete = {
                projectViewModel.deleteProject(it)
            },
            onUpdate = {
                projectViewModel.updateProject(it)
            }
        )
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CalendarTopBar(padding, calendarViewModel, calendarState)
        CalendarWeekGrid(calendarState, projects, onDayClick = { day ->
            calendarViewModel.setSelectedDate(day.date)
            isModalVisible = true
        })
    }
}


