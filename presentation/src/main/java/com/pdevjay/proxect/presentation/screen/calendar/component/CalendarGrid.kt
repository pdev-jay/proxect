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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.presentation.screen.calendar.CalendarViewModel
import com.pdevjay.proxect.presentation.screen.calendar.model.CalendarState


@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun CalendarGrid(
    padding: PaddingValues,
    calendarState: CalendarState,
    viewModel: CalendarViewModel
) {
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
        // 날짜 표시 (6행 7열)
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val cellHeight = maxHeight / 6  // 7열

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(calendarState.days) { calendarDay ->
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .height(cellHeight)
                            .clickable {
//                                viewModel.selectDate(calendarDay.date)
                            },
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Text(
                            text = calendarDay.date.dayOfMonth.toString(),
                            color = when {
                                calendarDay.isToday -> Color.Red
                                calendarDay.isCurrentMonth -> MaterialTheme.colorScheme.onBackground
                                else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                            }
                        )
                    }
                }
            }
        }
    }
}