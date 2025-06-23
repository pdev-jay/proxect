package com.pdevjay.proxect.presentation.screen.project.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

enum class TargetType(val label: String) {
    PROJECT("프로젝트"),
    COMMENT("댓글"),
    TODO("할 일")
}


@Composable
fun ConfirmEditDialog(
    projectName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("프로젝트 수정") },
        text = {
            Text("\"$projectName\" 프로젝트의 변경 사항을 저장하시겠습니까?")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("저장", color = MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}

@Composable
fun ConfirmEditCancelDialog(
    targetName: String,
    targetType: TargetType = TargetType.PROJECT,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("${targetType.label} 수정 취소") },
        text = { Text("\"$targetName\" ${targetType.label}의 변경 사항을 취소하시겠습니까?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("확인", color = MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}


@Composable
fun ConfirmDeleteDialog(
    targetName: String,
    targetType: TargetType = TargetType.PROJECT,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("${targetType.label} 삭제") },
        text = {
            Text("\"$targetName\" ${targetType.label}을(를) 정말 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("삭제", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}
