package com.pdevjay.proxect.presentation.screen.add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.presentation.screen.calendar.util.dashedRectBorder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun ProjectAddScreen(
    viewModel: ProjectViewModel,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(System.currentTimeMillis()) }
    var endDate by remember { mutableStateOf(System.currentTimeMillis()) }

    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    var selectedColor by remember { mutableStateOf(Color.Gray) }
    val colorOptions = listOf(
        Color(0xFFEF5350), // Red
        Color(0xFF42A5F5), // Blue
        Color(0xFF66BB6A), // Green
        Color(0xFFFFCA28), // Yellow
        Color(0xFFAB47BC), // Purple
        Color.Gray         // Default
    )


    // 날짜 포맷 함수
    val formatDate: (Long) -> String = {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(it))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("프로젝트", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("프로젝트 이름") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier)

        Text("내용", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("설명") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 10
        )

        Spacer(modifier = Modifier)
        Text(
            "시작일: ${formatDate(startDate)}",
            modifier = Modifier.clickable { showStartPicker = true }
        )
        Spacer(modifier = Modifier)
        Text(
            "종료일: ${formatDate(endDate)}",
            modifier = Modifier.clickable { showEndPicker = true }
        )

        Spacer(modifier = Modifier)
        Text("색상 선택", style = MaterialTheme.typography.titleMedium)

        Row(
            modifier = Modifier.fillMaxWidth()
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
                        .clickable { selectedColor = color }
                )
            }
        }
        Spacer(modifier = Modifier)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .dashedRectBorder(cornerRadius = 8.dp)
                .clickable(enabled = title.isNotBlank()) {
                    val project = Project(
                        id = UUID.randomUUID().toString(),
                        name = title,
                        description = description,
                        startDate = startDate,
                        endDate = endDate,
                        color = selectedColor.toArgb().toLong()
                    )
                    viewModel.addProject(project)
                    title = ""
                    description = ""
                    onBack()
                }
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Add, contentDescription = "추가")
        }


    }
    if (showStartPicker) {
        DatePickerDialogWrapper(
            initialDateMillis = startDate,
            onDismiss = { showStartPicker = false },
            onConfirm = {
                startDate = it
                if (endDate < startDate) endDate = it
            }
        )
    }

    if (showEndPicker) {
        DatePickerDialogWrapper(
            initialDateMillis = endDate,
            onDismiss = { showEndPicker = false },
            onConfirm = { endDate = it },
            dateValidator = { it >= startDate }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogWrapper(
    initialDateMillis: Long,
    onDismiss: () -> Unit,
    onConfirm: (Long) -> Unit,
    dateValidator: ((Long) -> Boolean)? = null
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return dateValidator?.invoke(utcTimeMillis) ?: true
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let(onConfirm)
                onDismiss()
            }) { Text("확인") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
