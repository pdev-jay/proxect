package com.pdevjay.proxect.presentation.screen.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.utils.toUTCLocalDate
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.screen.calendar.model.DialogContentType
import com.pdevjay.proxect.presentation.screen.common.ProjectCard
import com.pdevjay.proxect.presentation.screen.common.ProjectDialog
import kotlinx.coroutines.FlowPreview
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun ProjectListScreen(
    navController: NavController,
    projectListViewModel: ProjectListViewModel = hiltViewModel()
) {
    val projects by projectListViewModel.visibleProjects.collectAsState()
    val isLoadingPast by projectListViewModel.isLoadingPast.collectAsState()
    val isLoadingFuture by projectListViewModel.isLoadingFuture.collectAsState()

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var isModalVisible by remember { mutableStateOf(false) }
    var selectedProject by remember { mutableStateOf<ProjectForPresentation?>(null) }

    val topState = rememberPullToRefreshState()
    val bottomState = rememberPullToRefreshState()
    val listState = rememberLazyListState()

    var savedIndex by remember { mutableStateOf(0) }
    var savedOffset by remember { mutableStateOf(0) }
    var lastCount by remember { mutableStateOf(0) }

    // 날짜별로 그룹핑
    val groupedProjects = projects.groupBy { it.startDate.toUTCLocalDate() }


    LaunchedEffect(isLoadingPast) {

        if (!isLoadingPast) {
            if (lastCount == 0) {
                listState.scrollToItem(0)
            } else {
                listState.scrollToItem(
                    savedIndex + (projects.size - lastCount).coerceAtLeast(0) - 1,
                    savedOffset
                )
            }
        }
    }

    // 👇 Push to Refresh: 맨 아래 도달 시 미래 프로젝트 로드
    LaunchedEffect(listState) {
        snapshotFlow { listState.canScrollForward }
            .collect { canScroll ->
                if (!canScroll && !isLoadingFuture) {
                    projectListViewModel.loadMoreFutureProjects()
                }
            }
    }

    if (isModalVisible && selectedDate != null) {
        ProjectDialog(
            navController = navController,
            initialContentType = DialogContentType.ProjectDetail,
            selectedDate = selectedDate!!,
            initialSelectedProject = selectedProject,
            onDismiss = {
                isModalVisible = false
            },
            onDelete = {
                projectListViewModel.deleteProject(it)
            },
            onUpdate = {
                projectListViewModel.updateProject(it)
            }
        )

    }


    PullToRefreshBox(
        state = topState,
        onRefresh = {
            savedIndex = listState.firstVisibleItemIndex
            savedOffset = listState.firstVisibleItemScrollOffset
            lastCount = projects.size
            projectListViewModel.loadMorePastProjects()
        },
        isRefreshing = isLoadingPast,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isLoadingPast,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                state = topState
            )
        },
        modifier = Modifier.fillMaxSize(),

        ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            groupedProjects.forEach { (date, projectList) ->
                // 날짜 헤더
//                item {
//                    Text(
//                        text = date.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일")),
//                        style = MaterialTheme.typography.titleMedium,
//                        modifier = Modifier.padding(vertical = 8.dp)
//                    )
//                }

                // 프로젝트 카드들
                itemsIndexed(projectList) { index, project ->
                    Column {
                        if (index == 0) {
                            Text(
                                text = date.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일")),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        ProjectCard(project = project) {
                            selectedProject = project
                            selectedDate = project.startDate.toUTCLocalDate()
                            isModalVisible = true
                        }
                    }
                }
            }
            if (isLoadingFuture) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

        }
    }
}
