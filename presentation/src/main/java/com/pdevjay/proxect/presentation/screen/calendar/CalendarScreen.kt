package com.pdevjay.proxect.presentation.screen.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdevjay.proxect.presentation.LocalTopBarSetter
import com.pdevjay.proxect.presentation.TopAppBarData
import com.pdevjay.proxect.presentation.navigation.NavSharedViewModel
import com.pdevjay.proxect.presentation.screen.calendar.component.CalendarTopBar
import com.pdevjay.proxect.presentation.screen.calendar.component.CalendarWeekGrid
import com.pdevjay.proxect.presentation.screen.calendar.util.filterProjectsInDate
import com.pdevjay.proxect.presentation.screen.calendar.util.sortProject
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navSharedViewModel: NavSharedViewModel,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    projectViewModel: ProjectViewModel = hiltViewModel(),
    onNavigateToList: () -> Unit = {},
) {
    val calendarState by calendarViewModel.state.collectAsState()
    val projects by projectViewModel.projects.collectAsState()

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val setTopBar = LocalTopBarSetter.current

    LaunchedEffect(Unit) {
        setTopBar(
            TopAppBarData(
                title = "Proxect",
                showBack = false,
                actions = {
                }
            )
        )
    }

    LaunchedEffect(calendarState.days) {
        projectViewModel.loadProjects(
            calendarState.days.first().date, calendarState.days.last().date
        ) // month 기준 1일
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CalendarTopBar(calendarViewModel, calendarState)

        CalendarWeekGrid(calendarState, projects, onDayClick = { day ->
            selectedDate = day.date

            val projectsInDate = filterProjectsInDate(projects, day.date)
            val sortedProjects = sortProject(projectsInDate)

            navSharedViewModel.setProjects(sortedProjects)

            onNavigateToList()
        })
    }
}

