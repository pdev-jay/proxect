package com.pdevjay.proxect.presentation.screen.lists

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdevjay.proxect.presentation.navigation.BottomNavItem.Companion.items
import com.pdevjay.proxect.presentation.screen.calendar.component.toLocalDate
import com.pdevjay.proxect.presentation.screen.common.ProjectCard
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun ProjectListScreen(viewModel: ProjectListViewModel = hiltViewModel()) {
    val projects by viewModel.visibleProjects.collectAsState()
    val isLoadingPast by viewModel.isLoadingPast.collectAsState()
    val isLoadingFuture by viewModel.isLoadingFuture.collectAsState()

    val topState = rememberPullToRefreshState()
    val bottomState = rememberPullToRefreshState()
    val listState = rememberLazyListState()

    // 날짜별로 그룹핑
    val groupedProjects = projects.groupBy { it.startDate.toLocalDate() }


    // 👇 Push to Refresh: 맨 아래 도달 시 미래 프로젝트 로드
    LaunchedEffect(listState) {
        snapshotFlow { listState.canScrollForward }
            .collect { canScroll ->
                if (!canScroll && !isLoadingFuture) {
                    viewModel.loadMoreFutureProjects()
                }
            }
    }

    PullToRefreshBox(
        state = topState,
        onRefresh = { viewModel.loadMorePastProjects() },
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
            contentPadding = PaddingValues(16.dp)
        ) {
            groupedProjects.forEach { (date, projectList) ->
                // 날짜 헤더
                item {
                    Text(
                        text = date.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일")),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // 프로젝트 카드들
                items(projectList) { project ->
                    ProjectCard(project = project)
                }
            }
//                item {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 16.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//
//                        Indicator(
//                            modifier = Modifier.align(Alignment.Center).border(1.dp, Color.Red),
//                            isRefreshing = true,
//                            containerColor = MaterialTheme.colorScheme.primaryContainer,
//                            color = MaterialTheme.colorScheme.onPrimaryContainer,
//                            state = bottomState
//                        )
//                    }
//                }

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
