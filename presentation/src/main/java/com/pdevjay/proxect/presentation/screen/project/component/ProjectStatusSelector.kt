package com.pdevjay.proxect.presentation.screen.project.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.model.ProjectStatus
import com.pdevjay.proxect.domain.utils.toEpochMillis
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import java.time.LocalDate

@Composable
fun ProjectStatusSelector(
    projectToAdd: ProjectForPresentation,
    onChange: (ProjectForPresentation) -> Unit,
) {
    var selectedStatus by remember { mutableStateOf(ProjectStatus.NOT_STARTED) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)

    ) {
        for (status in ProjectStatus.entries) {
            Box(
                modifier = Modifier
                    .weight(1f)
//                    .padding(horizontal = 4.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        selectedStatus = status
                        onChange(
                            projectToAdd.copy(
                                status = selectedStatus,
                                finishedDate = if (selectedStatus == ProjectStatus.COMPLETED) LocalDate.now()
                                    .toEpochMillis() else null
                            )
                        )
                    }
                    .background(
                        color = if (status == selectedStatus)
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                        else
                            Color.Transparent
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "${status.displayName}",
                )
            }
        }
    }
}