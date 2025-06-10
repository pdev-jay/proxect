package com.pdevjay.proxect.presentation.screen.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.presentation.screen.calendar.component.toLocalDate
import com.pdevjay.proxect.presentation.screen.calendar.util.colorOptions
import com.pdevjay.proxect.presentation.screen.common.ColorPickerGrid
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun ProjectAddContent(
    viewModel: ProjectViewModel,
    onBack: () -> Unit,
    onChange: (Project) -> Unit
) {
    var projectToAdd by remember {
        mutableStateOf<Project>(
            Project(
                id = UUID.randomUUID().toString(),
                name = "",
                description = "",
                startDate = System.currentTimeMillis(),
                endDate = System.currentTimeMillis(),
            )
        )
    }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(System.currentTimeMillis()) }
    var endDate by remember { mutableStateOf(System.currentTimeMillis()) }

    var showDatePicker by remember { mutableStateOf(false) }

    var selectedColor by remember { mutableStateOf(Color(0xFFBDBDBD)) }

    // 날짜 포맷 함수
    val formatDate: (Long) -> String = {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(it))
    }

    Column(
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

                onChange(
                    projectToAdd
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
                onChange(
                    projectToAdd
                )
            },
            label = { Text("설명") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 10
        )

        Spacer(modifier = Modifier)

        Text("기간", style = MaterialTheme.typography.titleMedium)

        Text(
            "${formatDate(startDate)} - ${formatDate(endDate)}",
            modifier = Modifier.clickable { showDatePicker = true }
        )

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

                onChange(
                    projectToAdd
                )

            }
        )

//        Spacer(modifier = Modifier)
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .dashedRectBorder(cornerRadius = 8.dp)
//                .clickable(enabled = title.isNotBlank()) {
//                    val project = Project(
//                        id = UUID.randomUUID().toString(),
//                        name = title,
//                        description = description,
//                        startDate = startDate,
//                        endDate = endDate,
//                        color = selectedColor.toArgb().toLong()
//                    )
//                    viewModel.addProject(project)
//                    title = ""
//                    description = ""
//                    onBack()
//                }
//                .padding(12.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(Icons.Default.Add, contentDescription = "추가")
//        }


    }
    if (showDatePicker) {
        DatePickerDialogWrapper(
            initialStartDate = startDate,
            onDismiss = { showDatePicker = false },
            onDateRangeSelected = {
                startDate = it.first ?: System.currentTimeMillis()
                endDate = it.second ?: startDate
                projectToAdd = projectToAdd.copy(
                    startDate = startDate,
                    endDate = endDate
                )

                onChange(
                    projectToAdd
                )

            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogWrapper(
    initialStartDate: Long,
    onDismiss: () -> Unit,
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit = {}
) {
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = initialStartDate,
        initialSelectedEndDateMillis = initialStartDate,
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            showModeToggle = false,
            title = {
                Box(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = "Select date range"
                    )
                }
            },
            headline = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "${dateRangePickerState.selectedStartDateMillis?.toLocalDate() ?: "Start date"} - ${dateRangePickerState.selectedEndDateMillis?.toLocalDate() ?: "End date"}"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
//                .padding(4.dp)
        )
    }
}
