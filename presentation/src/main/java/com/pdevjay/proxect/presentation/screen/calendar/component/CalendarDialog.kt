package com.pdevjay.proxect.presentation.screen.calendar.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pdevjay.proxect.presentation.screen.calendar.CalendarViewModel
import com.pdevjay.proxect.presentation.screen.calendar.model.CalendarState

@Composable
fun CalendarDialog(
    viewModel: CalendarViewModel,
    calendarState: CalendarState
) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { viewModel.dismissModal() }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.85f) // 화면 90% 덮기 (원하면 .fillMaxSize()도 가능)
                .clip(RoundedCornerShape(16.dp)),
            tonalElevation = 8.dp,
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${calendarState.selectedDate}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = { viewModel.dismissModal() }) {
                        Icon(Icons.Default.Close, contentDescription = "닫기")
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .dashedRectBorder(cornerRadius = 8.dp)
                        .clickable { }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, contentDescription = "추가")
                }

                Spacer(modifier = Modifier.height(4.dp))
                Column(

                ) {
                    Text("여기에 입력 폼, 예: TextField, 버튼 등")
                }
            }
        }
    }
}

fun Modifier.dashedRectBorder(
    strokeWidth: Dp = 1.dp,
    color: Color = Color.Gray,
    dashLengths: FloatArray = floatArrayOf(20f, 10f), // [dash, gap]
    cornerRadius: Dp = 0.dp
): Modifier = this.then(
    Modifier.drawBehind {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(dashLengths, 0f)
        )
        val radius = cornerRadius.toPx()
        drawRoundRect(
            color = color,
            size = size,
            style = stroke,
            cornerRadius = CornerRadius(radius, radius)
        )
    }
)
