package com.pdevjay.proxect.presentation.screen.project.detail

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pdevjay.proxect.domain.model.ProjectStatus
import com.pdevjay.proxect.presentation.data.ProjectForPresentation

@Composable
fun ProjectStatusSelector(
    project: ProjectForPresentation,
    onStatusChanged: (ProjectStatus) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val options = ProjectStatus.entries.toList()
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(project.status) }
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier
//                .fillMaxWidth(0.9f)
//                .fillMaxHeight(0.5f)
                .clip(RoundedCornerShape(16.dp)),
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface

        ) {
            SingleChoiceSegmentedButtonRow {
                options.forEachIndexed { index, option ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        ),
                        onClick = { onOptionSelected(options[index]) },
                        selected = option == selectedOption,
                        label = { Text(option.displayName) }
                    )
                }
            }

        }
    }
}