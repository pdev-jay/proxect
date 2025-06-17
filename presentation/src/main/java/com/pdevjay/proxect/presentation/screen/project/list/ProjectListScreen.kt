package com.pdevjay.proxect.presentation.screen.project.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pdevjay.proxect.presentation.LocalTopBarSetter
import com.pdevjay.proxect.presentation.TopAppBarData
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.screen.common.ProjectCard

@Composable
fun ProjectListScreen(
    navController: NavController,
//    projectList: List<ProjectForPresentation>,
    onCardClick: (ProjectForPresentation) -> Unit = {}
) {
    val previousBackStackEntry = navController.previousBackStackEntry
    val result =
        previousBackStackEntry?.savedStateHandle?.get<List<ProjectForPresentation>>("project_list")
    var projectList by remember { mutableStateOf<List<ProjectForPresentation>>(emptyList()) }
    LaunchedEffect(result) {
        result?.let {
            projectList = it
            // 결과 처리 후, 재사용 방지를 위해 삭제
            previousBackStackEntry.savedStateHandle.remove<List<ProjectForPresentation>>("project_list")
            println("수정 결과 수신: $it")
        }
    }

    val setTopBar = LocalTopBarSetter.current

    LaunchedEffect(Unit) {
        setTopBar(
            TopAppBarData(
                title = "Proxect",
                showBack = true,
                actions = {

                }
            )
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(projectList) { project ->
            ProjectCard(project) {
                onCardClick(project)
            }
        }
    }
}