package com.pdevjay.proxect.presentation.screen.calendar.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.presentation.screen.calendar.model.CalendarDay
import com.pdevjay.proxect.presentation.screen.calendar.model.CalendarState
import com.pdevjay.proxect.presentation.screen.calendar.util.getProjectsForWeek

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun CalendarWeekGrid(
    calendarState: CalendarState,
    projects: List<Project>,
    onDayClick: (CalendarDay) -> Unit
) {
    val days = calendarState.days
    val projectsMapped = remember(projects) {
        projects.associateBy { it.id }
    }
    val density = LocalDensity.current

    AnimatedContent(
        targetState = calendarState.yearMonth,
        transitionSpec = {
            if (targetState > initialState) {
                slideInHorizontally { fullWidth -> fullWidth } + fadeIn() togetherWith
                        slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut()
            } else {
                slideInHorizontally { fullWidth -> -fullWidth } + fadeIn() togetherWith
                        slideOutHorizontally { fullWidth -> fullWidth } + fadeOut()
            }
        },
        label = "CalendarGridSlide"
    ) {

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val maxHeight = maxHeight
            val maxWidth = maxWidth
            val cellHeight = maxHeight / 6
            var textHeight by remember { mutableStateOf(0) }

            val dayCellHeight = with(density) { textHeight.toDp() }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(6) { weekIndex ->
                    val weekDates = days.subList(weekIndex * 7, (weekIndex + 1) * 7)
                    val weekProjects = getProjectsForWeek(weekDates, projects)

                    Column(
                        modifier = Modifier.height(cellHeight)
                    ) {
                        // 날짜 셀
                        Row(Modifier.fillMaxWidth()) {
                            weekDates.forEach { day ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = day.date.dayOfMonth.toString(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = when {
                                            day.isToday -> Color.Red
                                            day.isCurrentMonth -> MaterialTheme.colorScheme.onBackground
                                            else -> MaterialTheme.colorScheme.onBackground.copy(
                                                alpha = 0.3f
                                            )
                                        },
                                        onTextLayout = { textLayoutResult ->
                                            textHeight = textLayoutResult.size.height
                                        }
                                    )
                                }
                            }
                        }

                        // 프로젝트 바
//                    packedLines.forEach { lineProjects ->
//                        ProjectBarLineInWeek(weekDates, lineProjects, cellHeight - dayCellHeight)
//                    }
                        ProjectBarLineInWeek(
                            week = weekDates,
                            lineProjects = weekProjects, // 모든 프로젝트 전달
                            restHeight = cellHeight - dayCellHeight
                        )

                    }

                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(6) { weekIndex ->
                    val weekDates = days.subList(weekIndex * 7, (weekIndex + 1) * 7)

                    Row(Modifier.fillMaxWidth()) {
                        weekDates.forEach { day ->

                            Box(
                                modifier = Modifier.height(cellHeight).weight(1f)
                                    .clickable { onDayClick(day) },

                                )
                        }
                    }
                }
            }
        }
    }
}


