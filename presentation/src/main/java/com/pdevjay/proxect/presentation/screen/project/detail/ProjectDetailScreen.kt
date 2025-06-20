package com.pdevjay.proxect.presentation.screen.project.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.domain.utils.toUTCLocalDate
import com.pdevjay.proxect.presentation.LocalTopBarSetter
import com.pdevjay.proxect.presentation.TopAppBarData
import com.pdevjay.proxect.presentation.navigation.NavSharedViewModel
import com.pdevjay.proxect.presentation.screen.project.ProjectViewModel
import com.pdevjay.proxect.presentation.screen.project.component.ConfirmDeleteDialog
import com.pdevjay.proxect.presentation.screen.project.component.TargetType


@Composable
fun ProjectDetailScreen(
    navSharedViewModel: NavSharedViewModel,
    projectViewModel: ProjectViewModel,
    onNavigateToEdit: () -> Unit = {},
    onPopBackStack: () -> Unit = {}
) {
    val project by navSharedViewModel.selectedProject.collectAsState()
    val comments by projectViewModel.comments.collectAsState()
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var commentContent by remember { mutableStateOf("") }
    val setTopBar = LocalTopBarSetter.current

    LaunchedEffect(Unit) {
        setTopBar(
            TopAppBarData(
                title = "Proxect",
                showBack = true,
                actions = {
                    TextButton(
                        onClick = {
                            showDeleteConfirmDialog = true
                        }
                    ) {
                        Text(
                            "Delete",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Red
                        )
                    }

                    TextButton(
                        onClick = {
                            onNavigateToEdit()
                        }
                    ) {
                        Text("Edit", style = MaterialTheme.typography.titleMedium)
                    }
                }
            )
        )
    }

    LaunchedEffect(Unit) {
        projectViewModel.getComments(
            project!!.id,
            onSuccess = {},
            onFailure = { _, _ -> },
            onComplete = {}
        )


    }
    if (project != null) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("프로젝트", style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(project!!.name)
                Text(
                    "${project!!.status.displayName}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier)
            Text("기간", style = MaterialTheme.typography.titleMedium)
            Text("${project!!.startDate.toUTCLocalDate()} - ${project!!.endDate.toUTCLocalDate()}")
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier)
            Text(project!!.description)
            Spacer(modifier = Modifier)

            // Comments
            HorizontalDivider(color = Color.LightGray)
            Text("Comments", style = MaterialTheme.typography.titleMedium)
            CommentSection(
                comments,
                onAddComment = { newComment ->
                    commentContent = newComment
                    if (commentContent != "") {
                        projectViewModel.addComment(project!!.id, commentContent,
                            onSuccess = {
                                commentContent = ""
                            },
                            onFailure = { message, throwable ->
                            },
                            onComplete = {
                                projectViewModel.getComments(
                                    project!!.id,
                                    onSuccess = {},
                                    onFailure = { _, _ -> },
                                    onComplete = {}
                                )
                            }
                        )
                    }
                },
                onDeleteComment = { commentId, projectId ->
                    projectViewModel.deleteComment(projectId, commentId,
                        onSuccess = {},
                        onFailure = { message, throwable ->
                        },
                        onComplete = {
                            projectViewModel.getComments(
                                project!!.id,
                                onSuccess = {},
                                onFailure = { _, _ -> },
                                onComplete = {}
                            )
                        }
                    )
                },
                onUpdateComment = { commentId, projectId, newContent ->
                    projectViewModel.updateComment(projectId, commentId, newContent,
                        onSuccess = {},
                        onFailure = { message, throwable ->
                        },
                        onComplete = {
                            projectViewModel.getComments(
                                project!!.id,
                                onSuccess = {},
                                onFailure = { _, _ -> },
                                onComplete = {}
                            )
                        }
                    )
                })
//            TextField(
//                value = commentContent,
//                onValueChange = {
//                    commentContent = it
//                },
//                label = { Text("댓글 추가") },
//                modifier = Modifier.fillMaxWidth(),
//                minLines = 1,
//                trailingIcon = {
//                    Icon(
//                        Icons.Default.Add, "Add Comment",
//                        modifier = Modifier.clickable {
//                            projectViewModel.addComment(project!!.id, commentContent,
//                                onSuccess = {
//                                    commentContent = ""
//                                },
//                                onFailure = { message, throwable ->
//                                },
//                                onComplete = {
//                                    projectViewModel.getComments(
//                                        project!!.id,
//                                        onSuccess = {},
//                                        onFailure = { _, _ -> },
//                                        onComplete = {}
//                                    )
//                                }
//                            )
//                        },
//                    )
//                }
//            )
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                verticalArrangement = Arrangement.spacedBy(4.dp)
//            ) {
//                comments.forEach { comment ->
//                    Card(
//                        modifier = Modifier.background(Color.White),
//                        shape = RoundedCornerShape(12.dp),
//                        elevation = CardDefaults.cardElevation(1.dp),
//
//                        ) {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(4.dp),
//                        ) {
//                            Text("${comment.author}")
//                            Text("${comment.content}")
//                        }
//                    }
//
//                }
//            }
//
//
        }

        if (showDeleteConfirmDialog) {
            ConfirmDeleteDialog(
                targetName = project!!.name,
                targetType = TargetType.PROJECT,
                onConfirm = {
                    projectViewModel.deleteProject(project!!,
                        onSuccess = {
                            navSharedViewModel.deleteProject(project!!)
                        },
                        onFailure = { message, throwable ->

                        },
                        onComplete = {
                            showDeleteConfirmDialog = false
                            onPopBackStack()
                        }
                    )
                },
                onDismiss = {
                    showDeleteConfirmDialog = false
                }
            )
        }
    }
}