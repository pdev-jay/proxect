package com.pdevjay.proxect.presentation.screen.calendar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pdevjay.proxect.domain.utils.toUTCLocalDate
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.screen.calendar.component.CalendarTopBar
import com.pdevjay.proxect.presentation.screen.calendar.component.CalendarWeekGrid
import com.pdevjay.proxect.presentation.screen.calendar.util.sortProject
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavController,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    projectViewModel: ProjectViewModel = hiltViewModel(),
    onNavigateToList: (List<ProjectForPresentation>) -> Unit = {},
) {
    val calendarState by calendarViewModel.state.collectAsState()
    val projects by projectViewModel.projects.collectAsState()

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val padding = PaddingValues(4.dp)

//    val projectsInDate = remember(projects, selectedDate) {
//        selectedDate?.let { selected ->
//            val result = projects.filter {
//                it.startDate.toUTCLocalDate() <= selected && it.endDate.toUTCLocalDate() >= selected
//            }.sortedBy { it.startDate }
//            Log.e("listScreen", "5: ${result.size}")
//            result
//        } ?: emptyList()
//    }


    LaunchedEffect(calendarState.days) {
        projectViewModel.loadProjects(
            calendarState.days.first().date, calendarState.days.last().date
        ) // month 기준 1일
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CalendarTopBar(padding, calendarViewModel, calendarState)

        CalendarWeekGrid(calendarState, projects, onDayClick = { day ->
//            calendarViewModel.setSelectedDate(day.date)
            selectedDate = day.date

            val projectsInDate = filterProjectsInDate(projects, day.date)
            val sortedProjects = sortProject(projectsInDate)

            navController.currentBackStackEntry?.savedStateHandle?.set(
                "project_list",
                sortedProjects
            )
            Log.e("listScreen", "3: ${sortedProjects.size}")
            onNavigateToList(sortedProjects)

        })
    }
}

fun filterProjectsInDate(
    projects: List<ProjectForPresentation>,
    date: LocalDate
): List<ProjectForPresentation> {
    return projects.filter {
        it.startDate.toUTCLocalDate() <= date && it.endDate.toUTCLocalDate() >= date
    }.sortedBy { it.startDate }
}
