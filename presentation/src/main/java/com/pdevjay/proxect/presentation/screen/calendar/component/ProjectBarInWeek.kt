package com.pdevjay.proxect.presentation.screen.calendar.component

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.presentation.screen.calendar.model.CalendarDay
import com.pdevjay.proxect.presentation.screen.calendar.util.packProjectsIntoLines

@Composable
fun ProjectBarLineInWeek(
    week: List<CalendarDay>,
    lineProjects: List<Project>,
    restHeight: Dp
) {
    val maxLines = 4
    val barLines = maxLines - 1
    val verticalPadding = 2.dp
    val barHeight = restHeight / maxLines

    val totalDays = week.size

    // 1. 줄 단위로 나누기
    val lines = packProjectsIntoLines(week, lineProjects)

    val hiddenCount = IntArray(totalDays) { 0 }

    Column {
        // 2. 상위 4줄은 그대로 그림
        lines.take(barLines).forEach { line ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barHeight)
                    .padding(vertical = verticalPadding)
            ) {
                val barRanges = mutableListOf<Triple<Project, Int, Int>>()

                line.forEach { project ->
                    val start = project.startDate.toLocalDate()
                    val end = project.endDate.toLocalDate()

                    val startIndex = week.indexOfFirst { it.date in start..end }
                    val endIndex = week.indexOfLast { it.date in start..end }

                    if (startIndex == -1 || endIndex == -1 || endIndex < startIndex) return@forEach

                    barRanges.add(Triple(project, startIndex, endIndex))
                }

                var currentIndex = 0
                barRanges
                    .forEach { (project, startIndex, endIndex) ->
                    if (startIndex > currentIndex) {
                        Spacer(modifier = Modifier.weight((startIndex - currentIndex).toFloat()))
                    }

                    Box(
                        modifier = Modifier
                            .weight((endIndex - startIndex + 1).toFloat())
                            .fillMaxHeight()
                            .padding(horizontal = 2.dp)
                            .background(Color(project.color), RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.CenterStart
                    ){
                        BasicText(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            text = project.name,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                textAlign = TextAlign.Center
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    currentIndex = endIndex + 1
                }

                if (currentIndex < totalDays) {
                    Spacer(modifier = Modifier.weight((totalDays - currentIndex).toFloat()))
                }
            }
        }

        // 3. 초과 줄의 프로젝트들 → 날짜별 hiddenCount 계산
        lines.drop(barLines).flatten().forEach { project ->
            val start = project.startDate.toLocalDate()
            val end = project.endDate.toLocalDate()
            week.forEachIndexed { index, day ->
                if (day.date in start..end) {
                    hiddenCount[index]++
                }
            }
        }

        // 4. 마지막 줄에 +N more 그리기
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 0 until totalDays) {
                val count = hiddenCount[i]
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    if (count > 0) {
                        BasicText(
                            text = "+$count more",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.outline
                            ),
                        )
                    }
                }
            }
        }
    }
}
