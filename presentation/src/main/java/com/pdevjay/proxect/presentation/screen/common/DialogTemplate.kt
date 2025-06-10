package com.pdevjay.proxect.presentation.screen.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pdevjay.proxect.presentation.screen.calendar.model.DialogContentType

@Composable
fun DialogTemplate(
    onDismissRequest: () -> Unit,
    contentType: DialogContentType,
    titleContent: @Composable () -> Unit = {},
    content: @Composable (DialogContentType) -> Unit,
    bottomContent: @Composable () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.9f)
                .clip(RoundedCornerShape(16.dp)),
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                titleContent()

                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ){
                    AnimatedContent(
                        targetState = contentType,
                        transitionSpec = {
                            when {
                                initialState == DialogContentType.ProjectList && targetState == DialogContentType.ProjectDetail ||
                                        initialState == DialogContentType.ProjectDetail && targetState == DialogContentType.EditProject -> {
                                    // 앞으로 이동: 오른쪽에서 들어오고 왼쪽으로 나감
                                    slideInHorizontally { it } + fadeIn() togetherWith
                                            slideOutHorizontally { -it } + fadeOut()
                                }

                                initialState == DialogContentType.EditProject && targetState == DialogContentType.ProjectDetail ||
                                        initialState == DialogContentType.ProjectDetail && targetState == DialogContentType.ProjectList -> {
                                    // 뒤로 이동: 왼쪽에서 들어오고 오른쪽으로 나감
                                    slideInHorizontally { -it } + fadeIn() togetherWith
                                            slideOutHorizontally { it } + fadeOut()
                                }

                                else -> {
                                    // 기본 fallback (crossfade)
                                    fadeIn() togetherWith fadeOut()
                                }
                            }.using(SizeTransform(clip = false))
                        },
                        label = "DialogContentSlide"
                    ) { content ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                        ) {
                            content(content)
                        }
                    }
                }
                bottomContent()
            }
        }
    }
}
