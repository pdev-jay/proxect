package com.pdevjay.proxect.presentation.screen.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.domain.model.ProjectStatus
import com.pdevjay.proxect.domain.utils.toEpochMillis
import com.pdevjay.proxect.presentation.screen.common.BasicContainer
import com.pdevjay.proxect.presentation.screen.common.ProjectCard
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

enum class ProjectFilter(val code: Int, val displayName: String) {
    DUE_TODAY(0, "오늘 마감"),
    DUE_THIS_WEEK(1, "이번주 마감"),
    UPCOMING(2, "예정"),
    COMPLETED(3, "완료");

    companion object {
        fun fromCode(code: Int): ProjectFilter =
            entries.firstOrNull { it.code == code } ?: DUE_TODAY
    }
}

@Composable
fun DashboardScreen(
    projectViewModel: ProjectViewModel
) {
    val projects by projectViewModel.projects.collectAsState()
    var selectedFilter by remember { mutableStateOf(ProjectFilter.DUE_TODAY) }

    val today = remember { LocalDate.now().toEpochMillis() }
    var dueTodayCount = remember { projects.count { it.endDate == today } }
    val filteredProjects = remember(projects, selectedFilter) {
        when (selectedFilter) {
            ProjectFilter.DUE_TODAY -> {
                dueTodayCount = projects.count { it.endDate == today }
                projects.filter {
                    it.endDate == today
                }
            }

            ProjectFilter.DUE_THIS_WEEK -> {
                val endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                    .toEpochMillis()
                projects.filter {
                    it.endDate in today..endOfWeek
                }
            }

            ProjectFilter.UPCOMING -> {
                projects.filter {
                    it.startDate > today && it.status != ProjectStatus.COMPLETED
                }
            }

            ProjectFilter.COMPLETED -> {
                projects.filter {
                    it.status == ProjectStatus.COMPLETED
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "${dueTodayCount} projects due today!",
            style = MaterialTheme.typography.headlineSmall
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Color.LightGray)

        BasicContainer {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)

            ) {
                for (filter in ProjectFilter.entries) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                selectedFilter = filter
                            }
                            .background(
                                color = if (filter == selectedFilter)
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                else
                                    Color.Transparent
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "${filter.displayName}",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        BasicContainer(
            cornerRadius = 12.dp,
            padding = 8.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedContent(
                targetState = filteredProjects,
                modifier = Modifier.fillMaxSize()
            ) { projectState ->
                LazyColumn {
                    items(projectState) { project ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ProjectCard(project)
                        }
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DashboardScreenPreview() {
//    DashboardScreen()
//}