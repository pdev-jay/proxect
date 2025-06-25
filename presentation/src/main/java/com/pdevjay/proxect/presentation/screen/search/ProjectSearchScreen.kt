package com.pdevjay.proxect.presentation.screen.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdevjay.proxect.domain.model.ProjectStatus
import com.pdevjay.proxect.domain.utils.toEpochMillis
import com.pdevjay.proxect.domain.utils.toUTCLocalDate
import com.pdevjay.proxect.presentation.LocalTopBarSetter
import com.pdevjay.proxect.presentation.TopAppBarData
import com.pdevjay.proxect.presentation.navigation.NavSharedViewModel
import com.pdevjay.proxect.presentation.screen.common.BasicContainer
import com.pdevjay.proxect.presentation.screen.common.ProjectCard
import com.pdevjay.proxect.presentation.screen.project.component.DatePickerDialogWrapper
import kotlinx.coroutines.FlowPreview
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun ProjectSearchScreen(
    navSharedViewModel: NavSharedViewModel,
    projectSearchViewModel: ProjectSearchViewModel = hiltViewModel(),
    onNavigateToProjectDetail: () -> Unit = {},
) {
    val searchedProjects by projectSearchViewModel.searchedProjects.collectAsState()

    val searchState by projectSearchViewModel.searchState.collectAsState()


    val isStatusFilterActive = searchState.isStatusFilterActive
    val isDateFilterActive = searchState.isDateFilterActive
    val startDate = searchState.startDate
    val endDate = searchState.endDate

    val isSearchBarActive = rememberSaveable { mutableStateOf(false) }
    var showStatusSelector by remember { mutableStateOf(false) }
    val statusOptions = listOf(null) + ProjectStatus.entries.toList()
    var selectedStatus by remember { mutableStateOf<ProjectStatus?>(null) }

    var showDatePicker by remember { mutableStateOf(false) }

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

    LaunchedEffect(Unit) {
        projectSearchViewModel.searchProjectsWithDates(
            startDate = LocalDate.now().minusWeeks(2),
            endDate = LocalDate.now().plusWeeks(2),
            onSuccess = {},
            onFailure = { message, throwable ->
            },
            onComplete = {}
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                isSearchBarActive.value = false
            },
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProjectSearchBar(
            isSearchBarActive = isSearchBarActive,
            onSearch = { query ->

                projectSearchViewModel.updateSearchState(searchQuery = query)
            },
            onQueryChange = { query ->
                projectSearchViewModel.updateSearchState(searchQuery = query)
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ),
            ) {
                Checkbox(
                    checked = isStatusFilterActive,
                    onCheckedChange = {
                        projectSearchViewModel.updateSearchState(isStatusFilterActive = !isStatusFilterActive)
//                        isStatusFilterActive = !isStatusFilterActive
                    }
                )
                Box {
                    TextButton(
                        enabled = isStatusFilterActive,
                        onClick = {
                            showStatusSelector = !showStatusSelector
                        }
                    ) {
                        Text("${selectedStatus?.displayName ?: "모든 상태"}")
                    }
                    DropdownMenu(
                        expanded = showStatusSelector,
                        onDismissRequest = { showStatusSelector = false }
                    ) {
                        statusOptions.forEach { status ->
                            DropdownMenuItem(
                                modifier = Modifier
                                    .background(
                                        if (status == selectedStatus) MaterialTheme.colorScheme.surfaceContainerHighest else Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                text = { Text(status?.displayName ?: "모든 상태") },
                                onClick = {
                                    if (selectedStatus != status) {
                                        projectSearchViewModel.updateSearchState(
                                            isStatusFilterActive = true,
                                            projectStatus = status
                                        )
                                        selectedStatus = status
                                    }
                                    showStatusSelector = false
                                }
                            )
                        }
                    }

                }
            }
            Row(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ),
            ) {
                Checkbox(
                    checked = isDateFilterActive,
                    onCheckedChange = {
                        projectSearchViewModel.updateSearchState(isDateFilterActive = !isDateFilterActive)

//                        isDateFilterActive = !isDateFilterActive
                    }
                )
                TextButton(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                    enabled = isDateFilterActive,
                    onClick = {
                        showDatePicker = true
                    }
                ) {
                    Text("${startDate.toUTCLocalDate()} - ${endDate.toUTCLocalDate()}")
                }
            }
        }

        // 프로젝트 리스트 부분
        BasicContainer(
            cornerRadius = 12.dp,
            padding = 8.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedContent(
                targetState = searchedProjects
            ) { projects ->
                if (projects.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("검색 결과가 없습니다.")
                    }
                } else {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(projects) { project ->
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                ProjectCard(project) {
                                    navSharedViewModel.setProject(project)
                                    onNavigateToProjectDetail()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if (showDatePicker) {
        DatePickerDialogWrapper(
            initialStartDate = startDate,
            initialEndDate = endDate,
            onDismiss = { showDatePicker = false },
            onDateRangeSelected = {
                projectSearchViewModel.updateSearchState(
                    startDate = it.first ?: LocalDate.now().toEpochMillis(),
                    endDate = it.second ?: startDate
                )
            }
        )
    }

}
