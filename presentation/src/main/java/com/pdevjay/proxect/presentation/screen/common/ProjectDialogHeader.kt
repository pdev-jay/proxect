package com.pdevjay.proxect.presentation.screen.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.presentation.screen.calendar.model.DialogContentType
import java.time.LocalDate

@Composable
fun ProjectDialogHeader(
    contentType: DialogContentType,
    selectedDate: LocalDate,
    showBack: Boolean = true,
    onDismiss: () -> Unit = {},
    onEdit: () -> Unit = {},
    onConfirmEdit: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedContent(
            targetState = contentType,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "DialogTitle"
        ) { state ->
            when (state) {
                DialogContentType.ProjectList -> Text(
                    "$selectedDate",
                    style = MaterialTheme.typography.titleLarge
                )
                DialogContentType.ProjectDetail -> {
                    if (showBack) {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "뒤로가기"
                            )
                        }
                    }
                }
                DialogContentType.EditProject -> {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기"
                        )
                    }
                }

                DialogContentType.AddProject -> {

                }
            }
        }

        Row {
            when (contentType) {
                DialogContentType.ProjectDetail -> {
                    IconButton(onClick = { onEdit() }) {
                        Icon(Icons.Default.ModeEdit, contentDescription = "수정")
                    }
                }

                DialogContentType.EditProject -> {
                    IconButton(onClick = { onConfirmEdit() }) {
                        Icon(Icons.Default.Check, contentDescription = "완료")
                    }
                }

                DialogContentType.ProjectList -> {
                    IconButton(onClick = { onDismiss() }) {
                        Icon(Icons.Default.Close, contentDescription = "닫기")
                    }
                }

                else -> {
                    IconButton(onClick = { onDismiss() }) {
                        Icon(Icons.Default.Close, contentDescription = "닫기")
                    }
                }
            }
        }
    }
}
