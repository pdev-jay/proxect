package com.pdevjay.proxect.presentation.screen.project.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pdevjay.proxect.presentation.LocalTopBarSetter
import com.pdevjay.proxect.presentation.TopAppBarData
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.navigation.NavSharedViewModel
import com.pdevjay.proxect.presentation.screen.common.ProjectCard

@Composable
fun ProjectListScreen(
    navController: NavController,
    navSharedViewModel: NavSharedViewModel,
    projectList: List<ProjectForPresentation>,
    onCardClick: (ProjectForPresentation) -> Unit = {}
) {
//    val selectedProjects by navSharedViewModel.selectedProjects.collectAsState()
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