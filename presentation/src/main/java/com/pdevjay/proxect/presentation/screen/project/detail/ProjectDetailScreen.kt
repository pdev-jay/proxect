package com.pdevjay.proxect.presentation.screen.project.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdevjay.proxect.domain.utils.toUTCLocalDate
import com.pdevjay.proxect.presentation.LocalTopBarSetter
import com.pdevjay.proxect.presentation.TopAppBarData
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.navigation.NavSharedViewModel
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel
import com.pdevjay.proxect.presentation.screen.project.component.ConfirmDeleteDialog


@Composable
fun ProjectDetailScreen(
    navController: NavController,
    navSharedViewModel: NavSharedViewModel,
//    project: ProjectForPresentation,
    projectViewModel: ProjectViewModel,
    onNavigateToEdit: () -> Unit = {}
) {
//    var newProject by remember { mutableStateOf(project.copy()) }
    val project by navSharedViewModel.selectedProject.collectAsState()

    val currentBackStackEntry = navController.currentBackStackEntry
    val result = currentBackStackEntry?.savedStateHandle?.get<ProjectForPresentation>("edit_result")

    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    val setTopBar = LocalTopBarSetter.current

//    LaunchedEffect(result) {
//        result?.let {
//            newProject = it
//            // 결과 처리 후, 재사용 방지를 위해 삭제
//            currentBackStackEntry.savedStateHandle.remove<ProjectForPresentation>("edit_result")
//            println("수정 결과 수신: $it")
//        }
//    }

    LaunchedEffect(Unit) {
        setTopBar(
            TopAppBarData(
                title = "Proxect",
                showBack = true,
                actions = {
                    TextButton(
                        onClick = {
                            showDeleteConfirmDialog = true
                        }
                    ) {
                        Text(
                            "Delete",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Red
                        )
                    }

                    TextButton(
                        onClick = {
                            onNavigateToEdit()
                        }
                    ) {
                        Text("Edit", style = MaterialTheme.typography.titleMedium)
                    }
                }
            )
        )
    }

    if (project != null) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("프로젝트", style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(project!!.name)
                Text(
                    "${project!!.status.displayName}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier)
            Text("기간", style = MaterialTheme.typography.titleMedium)
            Text("${project!!.startDate.toUTCLocalDate()} - ${project!!.endDate.toUTCLocalDate()}")
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier)
            Text(project!!.description)
            Spacer(modifier = Modifier)
        }

        if (showDeleteConfirmDialog) {
            ConfirmDeleteDialog(
                projectName = project!!.name,
                onConfirm = {
                    showDeleteConfirmDialog = false
                    projectViewModel.deleteProject(project!!)
                    navController.popBackStack()
                },
                onDismiss = {
                    showDeleteConfirmDialog = false
                }
            )
        }
    }
}