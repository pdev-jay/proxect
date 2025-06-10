package com.pdevjay.proxect.presentation.screen.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.presentation.screen.calendar.model.DialogContentType
import com.pdevjay.proxect.presentation.screen.common.DialogTemplate
import com.pdevjay.proxect.presentation.screen.common.ProjectDialogHeader
import java.time.LocalDate

@Composable
fun ProjectAddDialog(
    projectToAdd: Project?,
    onDismiss: () -> Unit,
    onChange: (Project) -> Unit,
    onConfirm: () -> Unit,
    viewModel: ProjectViewModel
) {
    DialogTemplate(
        onDismissRequest = onDismiss,
        contentType = DialogContentType.AddProject,
        titleContent = {
            ProjectDialogHeader(
                contentType = DialogContentType.AddProject,
                selectedDate = LocalDate.now(),
                showBack = false,
                onDismiss = onDismiss,
            )
        },
        content = {
            ProjectAddContent(
                viewModel = viewModel,
                onBack = onDismiss,
                onChange = onChange
            )
        },
        bottomContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onConfirm) {
                    Text("저장")
                }
            }
        }
    )
}
