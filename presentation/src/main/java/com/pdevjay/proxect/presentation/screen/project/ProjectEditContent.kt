package com.pdevjay.proxect.presentation.screen.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.utils.formatDate
import com.pdevjay.proxect.domain.utils.toEpochMillis
import com.pdevjay.proxect.presentation.screen.calendar.util.colorOptions
import com.pdevjay.proxect.presentation.screen.common.ColorPickerGrid
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@Composable
fun ProjectEditContent(
    project: Project,
    onChange: (Project) -> Unit,
) {
    var title by remember { mutableStateOf(project.name) }
    var description by remember { mutableStateOf(project.description) }
    var startDate by remember { mutableStateOf(project.startDate) }
    var endDate by remember { mutableStateOf(project.endDate) }
    var selectedColor by remember { mutableStateOf(Color(project.color.toInt())) }

    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("프로젝트", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                onChange(project.copy(name = title))
            },
            label = { Text("프로젝트 이름") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("내용", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
                onChange(project.copy(description = description))
            },
            label = { Text("설명") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 10
        )

        Text("기간", style = MaterialTheme.typography.titleMedium)

        Text(
            "${formatDate(startDate)} - ${formatDate(endDate)}",
            modifier = Modifier.clickable { showDatePicker = true }
        )

        Text("색상 선택", style = MaterialTheme.typography.titleMedium)
        ColorPickerGrid(
            colorOptions = colorOptions,
            selectedColor = selectedColor,
            onColorSelected = {
                selectedColor = it
                onChange(project.copy(color = selectedColor.toArgb().toLong()))
            }
        )

    }

    if (showDatePicker) {
        DatePickerDialogWrapper(
            initialStartDate = startDate,
            onDismiss = { showDatePicker = false },
            onDateRangeSelected = {
                startDate = it.first ?: LocalDate.now().toEpochMillis()
                endDate = it.second ?: LocalDate.now().toEpochMillis()

                onChange(project.copy(startDate = startDate, endDate = endDate))
            }
        )
    }
}
