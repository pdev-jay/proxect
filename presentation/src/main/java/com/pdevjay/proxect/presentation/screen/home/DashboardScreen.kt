package com.pdevjay.proxect.presentation.screen.home

import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import com.pdevjay.proxect.domain.model.ProjectStatus
import com.pdevjay.proxect.domain.utils.toEpochMillis
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.screen.common.BasicContainer
import com.pdevjay.proxect.presentation.screen.common.ProjectCard
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel
import java.time.LocalDate

data class DashboardUiState(
    val filteredProjects: List<ProjectForPresentation> = emptyList(),
    val isLoading: Boolean = false,
    val message: String = "",
    val emptyMessage: String = "",
    val projectCount: Int = 0
)

data class Quadruple<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)

enum class ProjectFilter(val code: Int, val displayName: String) {
    NEEDS_ATTENTION(0, "마감 임박"),   // 오늘 확인이 필요한 우선순위
    OVERDUE(1, "지연"),              // 놓친 것부터 처리해야 하므로 위에
    IN_PROGRESS(2, "진행 중"),        // 주기적으로 관리되는 핵심 상태
    UPCOMING(3, "예정"),             // 다음 예정 확인
    COMPLETED(4, "완료");             // 마지막에 넣어도 무방 (아카이브 느낌)


    companion object {
        fun fromCode(code: Int): ProjectFilter =
            entries.firstOrNull { it.code == code } ?: IN_PROGRESS
    }
}

@Composable
fun DashboardScreen(
    navController: NavController,
    projectViewModel: ProjectViewModel
) {
    val projects by projectViewModel.projects.collectAsState()
    var selectedFilter by remember { mutableStateOf(ProjectFilter.IN_PROGRESS) }

    var uiState by remember {
        mutableStateOf(
            DashboardUiState(
                emptyList(),
                false,
                "",
                "",
                0
            )
        )
    }

    val today = LocalDate.now().toEpochMillis()

    LaunchedEffect(selectedFilter, projects) {
        uiState = DashboardUiState(isLoading = true)

        val (filtered, count, message, emptyMessage) = when (selectedFilter) {
            ProjectFilter.IN_PROGRESS -> {
                val filtered = projects.filter {
                    it.startDate <= today && it.endDate >= today &&
                            it.status != ProjectStatus.COMPLETED &&
                            it.status != ProjectStatus.NOT_STARTED
                }
                Quadruple(
                    filtered,
                    filtered.size,
                    "${filtered.size}개의 프로젝트가 진행 중입니다!",
                    "현재 진행 중인 프로젝트가 없습니다."
                )
            }

            ProjectFilter.COMPLETED -> {
                val filtered = projects.filter { it.status == ProjectStatus.COMPLETED }
                Quadruple(
                    filtered,
                    filtered.size,
                    "${filtered.size}개의 프로젝트가 완료되었습니다!",
                    "완료된 프로젝트가 없습니다."
                )
            }

            ProjectFilter.UPCOMING -> {
                val filtered = projects.filter {
                    it.startDate > today && it.status != ProjectStatus.COMPLETED
                }
                Quadruple(
                    filtered,
                    filtered.size,
                    "${filtered.size}개의 프로젝트가 예정되어 있습니다!",
                    "예정된 프로젝트가 없습니다."
                )
            }

            ProjectFilter.NEEDS_ATTENTION -> {
                val oneWeek = LocalDate.now().plusWeeks(1).toEpochMillis()
                val filtered = projects.filter {
                    it.endDate in today..oneWeek && it.status != ProjectStatus.COMPLETED
                }
                Quadruple(
                    filtered,
                    filtered.size,
                    "${filtered.size}개의 프로젝트가 곧 마감됩니다!",
                    "마감 임박한 프로젝트가 없습니다."
                )
            }

            ProjectFilter.OVERDUE -> {
                val filtered = projects.filter {
                    it.endDate < today && it.status != ProjectStatus.COMPLETED
                }
                Quadruple(
                    filtered,
                    filtered.size,
                    "${filtered.size}개의 프로젝트가 지연됐습니다!",
                    "지연된 프로젝트가 없습니다."
                )
            }
        }

        uiState = DashboardUiState(
            filtered,
            isLoading = false,
            message,
            emptyMessage,
            count
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "${LocalDate.now()}",
            style = MaterialTheme.typography.headlineLarge
        )

        AnimatedContent(
            targetState = uiState
        ) { state ->
            Text(
                text = state.message,
                style = MaterialTheme.typography.headlineSmall
            )
        }

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
                targetState = uiState,
                modifier = Modifier.fillMaxSize(),
            ) { projectState ->
                Log.e("projectState", "size : ${projectState.filteredProjects.size}")
                if (projectState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp))
                } else if (projectState.filteredProjects.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "${projectState.emptyMessage}"
                        )
                    }
                } else {
                    LazyColumn {
                        items(projectState.filteredProjects) { project ->
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                ProjectCard(project) {

                                }
                            }
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