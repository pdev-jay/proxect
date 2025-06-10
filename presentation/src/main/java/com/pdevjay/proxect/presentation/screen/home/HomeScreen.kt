package com.pdevjay.proxect.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel
import com.pdevjay.proxect.presentation.screen.calendar.component.toLocalDate
import com.pdevjay.proxect.presentation.screen.calendar.model.DialogContentType
import com.pdevjay.proxect.presentation.screen.common.ProjectDialog
import com.pdevjay.proxect.presentation.screen.common.ProjectCard
import java.time.LocalDate


@Composable
fun HomeScreen(projectViewModel: ProjectViewModel) {
    val projects by projectViewModel.projects.collectAsState()

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var isModalVisible by remember { mutableStateOf(false) }
    var selectedProject by remember { mutableStateOf<Project?>(null) }

    val today = remember { LocalDate.now() }

    val todayProjects = remember(projects) {
        projects.filter { it.startDate.toLocalDate() <= today && it.endDate.toLocalDate() >= today }
    }

    val otherProjects = remember(projects) {
        val oneWeekAgo = today.minusDays(7)
        projects.filter {
            it.endDate.toLocalDate() in oneWeekAgo..today.minusDays(1)
        }
    }

    val futureProjects = remember(projects) {
        val oneWeekLater = today.plusDays(7)
        projects.filter {
            it.startDate.toLocalDate() in today.plusDays(1)..oneWeekLater
        }
    }

    if (isModalVisible && selectedDate != null) {
        ProjectDialog(
            initialContentType = DialogContentType.ProjectDetail,
            selectedDate = selectedDate!!,
            initialSelectedProject = selectedProject,
            onDismiss = {
                isModalVisible = false
            },
            onDelete = {
                projectViewModel.deleteProject(it)
            },
            onUpdate = {
                projectViewModel.updateProject(it)
            }
        )

    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (todayProjects.isNotEmpty()) {
            item {
                Text(
                    text = "진행 중",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            items(todayProjects) { project ->
                ProjectCard(project = project){
                    selectedProject = project
                    selectedDate = project.startDate.toLocalDate()
                    isModalVisible = true
                }
            }
        }

        if (futureProjects.isNotEmpty()) {
            item {
                Text(
                    text = "시작 예정",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            items(futureProjects) { project ->
                ProjectCard(project = project){
                    selectedProject = project
                    selectedDate = project.startDate.toLocalDate()
                    isModalVisible = true
                }
            }
        }

        if (otherProjects.isNotEmpty()) {
            item {
                Text(
                    text = "종료된 프로젝트",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            items(otherProjects) { project ->
                ProjectCard(project = project){
                    selectedProject = project
                    selectedDate = project.startDate.toLocalDate()
                    isModalVisible = true
                }
            }
        }
    }
}


