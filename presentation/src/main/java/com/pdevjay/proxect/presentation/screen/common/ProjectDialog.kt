package com.pdevjay.proxect.presentation.screen.common

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.presentation.screen.calendar.component.toLocalDate
import com.pdevjay.proxect.presentation.screen.calendar.model.DialogContentType
import java.time.LocalDate

@Composable
fun ProjectDialog(
    initialContentType: DialogContentType? = DialogContentType.ProjectList,
    selectedDate: LocalDate,
    initialSelectedProject: Project? = null,
    projects: List<Project>? = null,
    onDismiss: () -> Unit,
) {
    var contentType by remember { mutableStateOf<DialogContentType>(initialContentType ?: DialogContentType.ProjectList) }
    var selectedProject by remember { mutableStateOf<Project?>(initialSelectedProject) }

    Dialog(properties = DialogProperties(
        usePlatformDefaultWidth = false,
        dismissOnBackPress = true
    ),
        onDismissRequest = {
            if (contentType == DialogContentType.ProjectDetail && projects != null) {
                contentType = DialogContentType.ProjectList
            }
            onDismiss()
        }) {

        if (contentType == DialogContentType.ProjectDetail && projects != null) {
            BackHandler {
                contentType = DialogContentType.ProjectList
            }
        }

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
                modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AnimatedContent(
                        targetState = contentType,
                        transitionSpec = {
                            fadeIn() togetherWith fadeOut()
                        },
                        label = "DialogTitle"
                    ) { content ->
                        when(content){
                            DialogContentType.ProjectList -> Text("${selectedDate}", style = MaterialTheme.typography.titleLarge)
                            DialogContentType.ProjectDetail -> Text("", style = MaterialTheme.typography.titleLarge)
                        }
                    }

                    IconButton(onClick = {
                        if (contentType == DialogContentType.ProjectDetail) {
                            contentType = DialogContentType.ProjectList
                        }
                        onDismiss()
                    }
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "닫기")
                    }
                }

                AnimatedContent(
                    targetState = contentType,
                    transitionSpec = {
                        if (targetState == DialogContentType.ProjectDetail) {
                            slideInHorizontally { width -> width } + fadeIn() togetherWith
                                    slideOutHorizontally { width -> -width } + fadeOut()
                        } else {
                            slideInHorizontally { width -> -width } + fadeIn() togetherWith
                                    slideOutHorizontally { width -> width } + fadeOut()
                        }.using(
                            SizeTransform(clip = false)
                        )
                    },
                    label = "DialogContentSlide"
                ) { content ->

                    when (content) {
                        DialogContentType.ProjectList -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())

                            ) {
                                if (projects != null) {
                                    for (project in projects) {
                                        ProjectCard(
                                            project,
                                            onClick = {
                                                selectedProject = project
                                                contentType = DialogContentType.ProjectDetail

                                            }
                                        )
                                    }
                                }
                            }
                        }

                        DialogContentType.ProjectDetail -> {
                            selectedProject?.let { project ->
                                Column(
                                    modifier = Modifier.fillMaxHeight()
                                        .verticalScroll(rememberScrollState())
                                    ,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text("프로젝트", style = MaterialTheme.typography.titleMedium)
                                        Text(project.name)
                                        HorizontalDivider(color = Color.LightGray)
                                        Spacer(modifier = Modifier)
                                        Text("기간", style = MaterialTheme.typography.titleMedium)
                                        Text("${project.startDate.toLocalDate()} - ${project.endDate.toLocalDate()}")
                                        HorizontalDivider(color = Color.LightGray)
                                        Spacer(modifier = Modifier)
                                        Text(project.description)
                                    }

                                    if (projects != null) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            IconButton(onClick = {
                                                contentType = DialogContentType.ProjectList
                                            }) {
                                                Icon(
                                                    Icons.AutoMirrored.Filled.ArrowBack,
                                                    contentDescription = "뒤로가기"
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

