package com.pdevjay.proxect.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.presentation.screen.add.ProjectViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun HomeScreen(viewModel: ProjectViewModel) {
    val projects by viewModel.projects.collectAsState()

    val today = remember { System.currentTimeMillis() }

    val todayProjects = remember(projects) {
        projects.filter { it.startDate <= today && it.endDate >= today }
    }

    val otherProjects = remember(projects) {
        projects.filter { it.endDate < today }
    }

    val futureProjects = remember(projects) {
        projects.filter { it.startDate > today }
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
                ProjectCard(project = project)
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
                ProjectCard(project = project)
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
                ProjectCard(project = project)
            }
        }
    }
}


@Composable
fun ProjectCard(project: Project) {
    val formatter = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = project.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${formatter.format(Date(project.startDate))} ~ ${
                    formatter.format(
                        Date(
                            project.endDate
                        )
                    )
                }",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            if (project.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = project.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
