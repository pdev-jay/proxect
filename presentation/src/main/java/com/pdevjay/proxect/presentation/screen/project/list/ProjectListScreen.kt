package com.pdevjay.proxect.presentation.screen.project.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.presentation.LocalTopBarSetter
import com.pdevjay.proxect.presentation.TopAppBarData
import com.pdevjay.proxect.presentation.navigation.NavSharedViewModel
import com.pdevjay.proxect.presentation.screen.common.ProjectCard

@Composable
fun ProjectListScreen(
    navSharedViewModel: NavSharedViewModel,
    onNavigateToDetail: () -> Unit = {}
) {
    val selectedProjects by navSharedViewModel.selectedProjects.collectAsState()
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
            .padding(8.dp)
    ) {
        items(selectedProjects) { project ->
            ProjectCard(project) {
                navSharedViewModel.setProject(project)
                onNavigateToDetail()
            }
        }
    }
}