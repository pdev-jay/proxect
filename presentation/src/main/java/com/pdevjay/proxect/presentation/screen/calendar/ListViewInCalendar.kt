package com.pdevjay.proxect.presentation.screen.calendar

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.screen.common.ProjectCard

@Composable
fun ListViewInCalendar(
    projectList: List<ProjectForPresentation>,
    onCardClick: (ProjectForPresentation) -> Unit = {}
) {

    AnimatedContent(
        targetState = projectList,
        label = "DialogContentSlide"
    ) { projects ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(projects) { project ->
                ProjectCard(project) {
                    onCardClick(project)
                }
            }
        }
    }

}