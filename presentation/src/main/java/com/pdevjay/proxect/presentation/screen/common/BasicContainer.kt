package com.pdevjay.proxect.presentation.screen.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BasicContainer(
    modifier: Modifier = Modifier,
//    backgroundColor: Color = Color.LightGray.copy(alpha = 0.2f),
    backgroundColor: Color = Color.LightGray.copy(alpha = 0.2f),
    padding: Dp = 0.dp,
    cornerRadius: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(padding)
            .clip(RoundedCornerShape(cornerRadius))
    ) {
        content()
    }
}