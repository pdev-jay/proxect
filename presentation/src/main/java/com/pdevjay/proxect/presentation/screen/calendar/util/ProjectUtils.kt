package com.pdevjay.proxect.presentation.screen.calendar.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.utils.toUTCLocalDate
import com.pdevjay.proxect.presentation.screen.calendar.model.CalendarDay
import java.time.temporal.ChronoUnit


fun sortProject(projects: List<Project>): List<Project> {
    return projects.sortedWith(
        compareBy<Project>(
            { it.startDate.toUTCLocalDate() } // 날짜 단위로만 비교
        ).thenByDescending {
            ChronoUnit.DAYS.between(it.startDate.toUTCLocalDate(), it.endDate.toUTCLocalDate())
        }
    )
}

fun packProjectsIntoLines(
    week: List<CalendarDay>,
    projects: List<Project>
): List<List<Project>> {
    val lines = mutableListOf<MutableList<Pair<Project, IntRange>>>() // 줄별로 프로젝트와 index 범위 보관

    val sortedProjects = sortProject(projects)

    for (project in sortedProjects) {
        val startDate = project.startDate.toUTCLocalDate()
        val endDate = project.endDate.toUTCLocalDate()

        // 이 주에서 해당 프로젝트가 차지하는 index 범위 (e.g. 2~5)
        val startIndex =
            week.indexOfFirst { !it.date.isAfter(startDate) && !it.date.isBefore(startDate) }
                .takeIf { it >= 0 } ?: week.indexOfFirst { it.date >= startDate }

        val endIndex = week.indexOfLast { !it.date.isAfter(endDate) }
            .takeIf { it >= 0 } ?: week.lastIndex

        val occupiedRange = startIndex..endIndex

        // 이 줄에 넣을 수 있는지 확인
        var placed = false
        for (line in lines) {
            val hasConflict = line.any { (_, otherRange) ->
                occupiedRange.intersect(otherRange).isNotEmpty()
            }

            if (!hasConflict) {
                line.add(project to occupiedRange)
                placed = true
                break
            }
        }

        if (!placed) {
            lines.add(mutableListOf(project to occupiedRange))
        }
    }

    // 최종적으로 프로젝트만 꺼내서 줄 리스트 반환
    return lines.map { it.map { pair -> pair.first } }
}


fun getProjectsForWeek(
    weekDates: List<CalendarDay>,
    projects: List<Project>
): List<Project> {
    val weekStart = weekDates.first().date
    val weekEnd = weekDates.last().date

    return projects.filter { project ->
        val start = project.startDate.toUTCLocalDate()
        val end = project.endDate.toUTCLocalDate()

        // 주 범위와 프로젝트 기간이 겹치는 경우 포함
        !end.isBefore(weekStart) && !start.isAfter(weekEnd)
    }
}

fun Modifier.dashedRectBorder(
    strokeWidth: Dp = 1.dp,
    color: Color = Color.Gray,
    dashLengths: FloatArray = floatArrayOf(10f, 10f), // [dash, gap]
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
