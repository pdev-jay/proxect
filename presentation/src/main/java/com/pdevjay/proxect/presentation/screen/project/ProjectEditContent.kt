package com.pdevjay.proxect.presentation.screen.project

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.domain.model.Project
import java.text.SimpleDateFormat
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

    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    val colorOptions = listOf(
        Color(0xFFEF5350), Color(0xFF42A5F5), Color(0xFF66BB6A),
        Color(0xFFFFCA28), Color(0xFFAB47BC), Color.Gray
    )

    val formatDate: (Long) -> String = {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(it))
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("프로젝트 수정", style = MaterialTheme.typography.titleMedium)

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

        Text(
            "시작일: ${formatDate(startDate)}",
            modifier = Modifier.clickable { showStartPicker = true })
        Text("종료일: ${formatDate(endDate)}", modifier = Modifier.clickable { showEndPicker = true })

        Text("색상 선택", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            colorOptions.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = if (color == selectedColor) 3.dp else 1.dp,
                            color = if (color == selectedColor) Color.Black else Color.LightGray,
                            shape = CircleShape
                        )
                        .clickable {
                            selectedColor = color
                            onChange(project.copy(color = selectedColor.toArgb().toLong()))
                        }
                )
            }
        }
    }

    if (showStartPicker) {
        DatePickerDialogWrapper(
            initialDateMillis = startDate,
            onDismiss = { showStartPicker = false },
            onConfirm = {
                startDate = it
                onChange(project.copy(startDate = startDate))
                if (endDate < it) endDate = it
            }
        )
    }

    if (showEndPicker) {
        DatePickerDialogWrapper(
            initialDateMillis = endDate,
            onDismiss = { showEndPicker = false },
            onConfirm = {
                endDate = it
                onChange(project.copy(endDate = endDate))
            },
            dateValidator = { it >= startDate }
        )
    }
}
