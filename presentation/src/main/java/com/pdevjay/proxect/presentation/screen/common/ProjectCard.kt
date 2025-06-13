package com.pdevjay.proxect.presentation.screen.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.model.ProjectStatus
import com.pdevjay.proxect.domain.utils.formatDate

@Composable
fun ProjectCard(project: Project, onClick: () -> Unit = {}) {
    val textColor = if (isColorDark(Color(project.color))) Color.White else Color.Black

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(project.color) // Project에 저장된 색상 사용
//        )
    ) {
        Column(
            modifier = Modifier
                .background(Color(project.color).copy(alpha = 0.9f))
                .clip(RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = project.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                BasicContainer(
                    padding = 6.dp,
                    cornerRadius = 4.dp,
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FiberManualRecord,
                            contentDescription = project.status.displayName,
                            tint = when (project.status) {
                                ProjectStatus.COMPLETED -> Color.Green.copy(alpha = 0.7f)
                                ProjectStatus.IN_PROGRESS -> Color.Cyan.copy(alpha = 0.7f)
                                ProjectStatus.NOT_STARTED -> Color.Yellow.copy(alpha = 0.7f)
                                ProjectStatus.ON_HOLD -> Color.Red.copy(alpha = 0.7f)
                                else -> Color.Gray.copy(alpha = 0.7f)
                            },
                            modifier = Modifier.size(8.dp)
                        )
                        Text(
                            "${project.status.displayName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = textColor
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${formatDate(project.startDate)} - ${formatDate(project.endDate)}",
                style = MaterialTheme.typography.bodySmall,
                color = textColor.copy(alpha = 0.8f)
            )
            if (project.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = project.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor.copy(alpha = 0.7f)
                )
            }
        }
    }
}


fun isColorDark(color: Color): Boolean {
    val luminance = color.luminance()
    return luminance < 0.5
}
