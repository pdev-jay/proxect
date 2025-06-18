package com.pdevjay.proxect.presentation.screen.project.edit

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdevjay.proxect.domain.utils.formatDate
import com.pdevjay.proxect.domain.utils.toEpochMillis
import com.pdevjay.proxect.presentation.LocalTopBarSetter
import com.pdevjay.proxect.presentation.TopAppBarData
import com.pdevjay.proxect.presentation.navigation.NavSharedViewModel
import com.pdevjay.proxect.presentation.screen.common.colorOptions
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel
import com.pdevjay.proxect.presentation.screen.project.component.ColorPickerGrid
import com.pdevjay.proxect.presentation.screen.project.component.ConfirmEditCancelDialog
import com.pdevjay.proxect.presentation.screen.project.component.ConfirmEditDialog
import com.pdevjay.proxect.presentation.screen.project.component.DatePickerDialogWrapper
import com.pdevjay.proxect.presentation.screen.project.component.ProjectStatusSelector
import java.time.LocalDate

@Composable
fun ProjectEditScreen(
    navController: NavController,
    navSharedViewModel: NavSharedViewModel,
//    project: ProjectForPresentation,
    projectViewModel: ProjectViewModel
) {
    val project by navSharedViewModel.selectedProject.collectAsState()
    val originalProject = project!!.copy()
    var newProject by remember { mutableStateOf(project!!.copy()) }

    var showUpdateConfirmDialog by remember { mutableStateOf(false) }
    var showUpdateCancelDialog by remember { mutableStateOf(false) }


    var showDatePicker by remember { mutableStateOf(false) }

    val setTopBar = LocalTopBarSetter.current

    LaunchedEffect(Unit) {
        setTopBar(
            TopAppBarData(
                title = "Proxect",
                showBack = true,
                onBack = {
                    if (newProject != originalProject) {
                        showUpdateCancelDialog = true
                    } else {
                        navController.popBackStack()
                    }
                },
                actions = {
                    TextButton(
                        enabled = newProject != originalProject,
                        onClick = {
                            if (newProject != originalProject) {
                                showUpdateConfirmDialog = true
                            }
                        }
                    ) {
                        Text("Confirm", style = MaterialTheme.typography.titleMedium)
                    }
                }
            )
        )
    }

    if (showUpdateCancelDialog) {
        ConfirmEditCancelDialog(
            projectName = originalProject.name,
            onConfirm = {
                showUpdateCancelDialog = false
                navController.popBackStack()
            },
            onDismiss = {
                showUpdateCancelDialog = false
            }
        )
    }

    if (showUpdateConfirmDialog) {
        ConfirmEditDialog(
            projectName = originalProject.name,
            onConfirm = {
                showUpdateConfirmDialog = false
                projectViewModel.updateProject(
                    newProject
                )
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("edit_result", newProject)

                navController.popBackStack()
            },
            onDismiss = {
                showUpdateConfirmDialog = false
            }
        )

    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("프로젝트", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = newProject.name,
            onValueChange = {
                newProject = newProject.copy(name = it)
            },
            label = { Text("프로젝트 이름") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("내용", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = newProject.description,
            onValueChange = {
                newProject = newProject.copy(description = it)
            },
            label = { Text("설명") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 5
        )

        Text("기간", style = MaterialTheme.typography.titleMedium)

        Box(
            modifier = Modifier
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable { showDatePicker = true }

        ) {
            Text(
                "${formatDate(newProject.startDate)} - ${formatDate(newProject.endDate)}",
                modifier = Modifier.padding(8.dp)
            )
        }

        Text("상태", style = MaterialTheme.typography.titleMedium)

        ProjectStatusSelector(newProject.status) { newStatus ->
            newProject = newProject.copy(status = newStatus)

        }


        Text("색상 선택", style = MaterialTheme.typography.titleMedium)
        ColorPickerGrid(
            colorOptions = colorOptions,
            selectedColor = Color(newProject.color),
            onColorSelected = {
                newProject = newProject.copy(color = it.toArgb().toLong())

            }
        )

    }

    if (showDatePicker) {
        DatePickerDialogWrapper(
            initialStartDate = newProject.startDate,
            initialEndDate = newProject.endDate,
            onDismiss = { showDatePicker = false },
            onDateRangeSelected = {
                val startDate = it.first ?: LocalDate.now().toEpochMillis()
                val endDate = it.second ?: LocalDate.now().toEpochMillis()

                newProject = newProject.copy(startDate = startDate, endDate = endDate)
            }
        )
    }
}