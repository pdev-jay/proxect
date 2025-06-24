package com.pdevjay.proxect.presentation.screen.project.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pdevjay.proxect.domain.model.ProjectStatus
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
    val todos by projectViewModel.todos.collectAsState()

    val doneCount = todos.count { it.isDone }
    val totalCount = todos.size
    val progress = if (totalCount == 0) 0f else doneCount.toFloat() / totalCount

    val statusOptions = ProjectStatus.entries.toList()
    var selectedStatus by remember { mutableStateOf(project?.status ?: ProjectStatus.NOT_STARTED) }


    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var showTodoDialog by remember { mutableStateOf(false) }
    var showCommentSection by remember { mutableStateOf(false) }
    var showStatusSelector by remember { mutableStateOf(false) }
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
                            color = MaterialTheme.colorScheme.error
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

        projectViewModel.getTodos(
            project!!.id,
            onSuccess = {},
            onFailure = { _, _ -> },
            onComplete = {}
        )

    }
    if (project != null) {
        Column(
            modifier = Modifier
                .padding(8.dp),

            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("프로젝트", style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(project!!.name)
                Box {
                    TextButton(
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.surfaceContainerHighest,
                            shape = RoundedCornerShape(8.dp)
                        ),
                        onClick = {
                            showStatusSelector = !showStatusSelector
                        }
                    ) {
                        Text(
                            "${project!!.status.displayName}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    DropdownMenu(
                        expanded = showStatusSelector,
                        onDismissRequest = { showStatusSelector = false }
                    ) {
                        statusOptions.forEach { status ->
                            DropdownMenuItem(
                                modifier = Modifier
                                    .background(
                                        if (status == project!!.status) MaterialTheme.colorScheme.surfaceContainerHighest else Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                text = { Text(status.displayName) },
                                onClick = {
                                    selectedStatus = status
                                    if (project!!.status != status) {
                                        projectViewModel.updateProject(
                                            project!!.copy(status = status),
                                            onSuccess = {
                                                navSharedViewModel.updateProject(
                                                    project!!.copy(
                                                        status = status
                                                    )
                                                )
                                            },
                                            onFailure = { message, throwable ->

                                            },
                                            onComplete = {
                                            }
                                        )
                                    }
                                    showStatusSelector = false
                                }
                            )
                        }
                    }
                }
            }
            HorizontalDivider(color = Color.LightGray)

            Spacer(modifier = Modifier)
            Text("기간", style = MaterialTheme.typography.titleMedium)
            Text("${project!!.startDate.toUTCLocalDate()} - ${project!!.endDate.toUTCLocalDate()}")
            HorizontalDivider(color = Color.LightGray)

            Spacer(modifier = Modifier)
            Text("내용", style = MaterialTheme.typography.titleMedium)
            Text(project!!.description)
            HorizontalDivider(color = Color.LightGray)

            // Todos
            Spacer(modifier = Modifier)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("Todo", style = MaterialTheme.typography.titleMedium)
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color.LightGray.copy(alpha = 0.3f),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text("$doneCount / $totalCount")
                    TextButton(
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.surfaceContainerHighest,
                            shape = RoundedCornerShape(8.dp)
                        ),
                        onClick = {
                            showTodoDialog = true
                        }) {
                        Text(" show todo")
                    }
                }


            }

            // Comments
            HorizontalDivider(color = Color.LightGray)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Comments",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(18.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${comments.size}",
                        modifier = Modifier.padding(start = 4.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.surfaceContainerHighest,
                        shape = RoundedCornerShape(8.dp)
                    ),
                    onClick = {
                        showCommentSection = true
                    }
                ) {
                    Text("show comments")
                }
            }

        }

        if (showCommentSection) {
            CommentSection(
                comments,
                onDismiss = {
                    showCommentSection = false
                },
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

        if (showTodoDialog) {
            TodoSection(
                todos,
                onDismiss = {
                    showTodoDialog = false
                },
                onAddTodo = { newTodo ->
                    if (newTodo != "") {
                        projectViewModel.addTodo(project!!.id, newTodo,
                            onSuccess = {
                            },
                            onFailure = { message, throwable ->
                            },
                            onComplete = {
                                projectViewModel.getTodos(
                                    project!!.id,
                                    onSuccess = {},
                                    onFailure = { _, _ -> },
                                    onComplete = {}
                                )
                            }
                        )
                    }
                },
                onDeleteTodo = { todoId, projectId ->
                    projectViewModel.deleteTodo(todoId, projectId,
                        onSuccess = {},
                        onFailure = { message, throwable ->
                        },
                        onComplete = {
                            projectViewModel.getTodos(
                                project!!.id,
                                onSuccess = {},
                                onFailure = { _, _ -> },
                                onComplete = {}
                            )
                        }

                    )
                },
                onUpdateTodo = { todoId, projectId, newContent, isDone ->
                    projectViewModel.updateTodo(todoId, projectId, newContent, isDone,
                        onSuccess = {},
                        onFailure = { message, throwable ->
                        },
                        onComplete = {
                            projectViewModel.getTodos(
                                project!!.id,
                                onSuccess = {},
                                onFailure = { _, _ -> },
                                onComplete = {}
                            )
                        }
                    )
                }
            )
        }
    }
}