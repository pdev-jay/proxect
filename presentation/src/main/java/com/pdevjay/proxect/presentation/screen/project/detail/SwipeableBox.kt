package com.pdevjay.proxect.presentation.screen.project.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

enum class SwipeState {
    Closed,
    Opened
}

@Composable
fun SwipeableBox(
    swipeState: AnchoredDraggableState<SwipeState>,
    swipeWidth: Dp = 96.dp,
    backgroundContent: @Composable BoxScope.() -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val swipeWidthPx = with(LocalDensity.current) { swipeWidth.toPx() }

    val offsetX = swipeState.offset
    val clampedOffset = if (offsetX.isNaN()) 0 else offsetX.roundToInt()

    LaunchedEffect(Unit) {
        swipeState.updateAnchors(
            DraggableAnchors {
                SwipeState.Closed at 0f
                SwipeState.Opened at -swipeWidthPx
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .anchoredDraggable(
                state = swipeState,
                orientation = Orientation.Horizontal
            )
    ) {
        backgroundContent()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(clampedOffset, 0) }
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                    }
                ),
        ) {
            content()
        }
    }
}
