package com.pdevjay.proxect.presentation.screen.project.add

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.pdevjay.proxect.domain.model.ProjectStatus
import com.pdevjay.proxect.domain.utils.formatDate
import com.pdevjay.proxect.domain.utils.toEpochMillis
import com.pdevjay.proxect.presentation.LocalTopBarSetter
import com.pdevjay.proxect.presentation.TopAppBarData
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.screen.common.colorOptions
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel
import com.pdevjay.proxect.presentation.screen.project.component.ColorPickerGrid
import com.pdevjay.proxect.presentation.screen.project.component.DatePickerDialogWrapper
import com.pdevjay.proxect.presentation.screen.project.component.ProjectStatusSelector
import java.time.LocalDate
import java.util.UUID

@Composable
fun ProjectAddScreen(
    navController: NavController,
//    navSharedViewModel: NavSharedViewModel,
    projectViewModel: ProjectViewModel
) {
    var projectToAdd by remember {
        mutableStateOf<ProjectForPresentation>(
            ProjectForPresentation(
                id = UUID.randomUUID().toString(),
                name = "",
                description = "",
                startDate = LocalDate.now().toEpochMillis(),
                endDate = LocalDate.now().toEpochMillis(),
            )
        )
    }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    /**
     * 현지 날짜 기준 시작 날짜 utc
     */
    var startDate by remember { mutableStateOf(LocalDate.now().toEpochMillis()) }

    /**
     * 현지 날짜 기준 끝 날짜 utc
     */
    var endDate by remember { mutableStateOf(LocalDate.now().toEpochMillis()) }

    var showDatePicker by remember { mutableStateOf(false) }

    var selectedColor by remember { mutableStateOf(Color(0xFFBDBDBD)) }

    var selectedStatus by remember { mutableStateOf(ProjectStatus.NOT_STARTED) }


    val setTopBar = LocalTopBarSetter.current

    LaunchedEffect(Unit) {
        setTopBar(
            TopAppBarData(
                title = "Proxect",
                showBack = true,
                actions = {
                    TextButton(
                        onClick = {
                            if (projectToAdd.name.isNotBlank()) {
                                projectViewModel.addProject(projectToAdd)
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Text("Save")
                    }
                }
            )
        )
    }
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("프로젝트", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                projectToAdd = projectToAdd.copy(
                    name = title
                )

            },
            label = { Text("프로젝트 이름") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier)

        Text("내용", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
                projectToAdd = projectToAdd.copy(
                    description = description
                )
            },
            label = { Text("설명") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 5
        )

        Spacer(modifier = Modifier)

        Text("기간", style = MaterialTheme.typography.titleMedium)

        Box(
            modifier = Modifier
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable { showDatePicker = true }

        ) {
            Text(
                "${formatDate(startDate)} - ${formatDate(endDate)}",
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier)
        Text("상태", style = MaterialTheme.typography.titleMedium)

        ProjectStatusSelector(selectedStatus) { newStatus ->
            selectedStatus = newStatus
            projectToAdd = projectToAdd.copy(
                status = selectedStatus
            )
        }

        Spacer(modifier = Modifier)
        Text("색상 선택", style = MaterialTheme.typography.titleMedium)

        ColorPickerGrid(
            colorOptions = colorOptions,
            selectedColor = selectedColor,
            onColorSelected = {
                selectedColor = it
                projectToAdd = projectToAdd.copy(
                    color = selectedColor.toArgb().toLong()
                )
            }
        )
    }
    if (showDatePicker) {
        DatePickerDialogWrapper(
            initialStartDate = startDate,
            initialEndDate = endDate,
            onDismiss = { showDatePicker = false },
            onDateRangeSelected = {
                startDate = it.first ?: LocalDate.now().toEpochMillis()
                endDate = it.second ?: startDate
                projectToAdd = projectToAdd.copy(
                    startDate = startDate,
                    endDate = endDate
                )
            }
        )
    }
}