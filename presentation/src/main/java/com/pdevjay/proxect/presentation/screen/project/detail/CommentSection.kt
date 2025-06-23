package com.pdevjay.proxect.presentation.screen.project.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pdevjay.proxect.presentation.data.CommentForPresentation
import com.pdevjay.proxect.presentation.screen.project.component.ConfirmDeleteDialog
import com.pdevjay.proxect.presentation.screen.project.component.ConfirmEditCancelDialog
import com.pdevjay.proxect.presentation.screen.project.component.TargetType
import kotlinx.coroutines.launch

@Composable
fun CommentSection(
    comments: List<CommentForPresentation>,
    onAddComment: (String) -> Unit,
    onDismiss: () -> Unit = {},
    onDeleteComment: (String, String) -> Unit = { _, _ -> },
    onUpdateComment: (String, String, String) -> Unit = { _, _, _ -> }
) {
    var commentText by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.5f)
                .clip(RoundedCornerShape(16.dp)),
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface
        ) {

            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                // 댓글 입력창
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        placeholder = { Text("댓글을 입력하세요") },
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent

                        )
                    )
                    IconButton(onClick = {
                        if (commentText.isNotBlank()) {
                            onAddComment(commentText.trim())
                            commentText = ""
                        }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Comment")
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn {
                    items(comments) {
                        CommentCard(
                            comment = it,
                            onDeleteComment = onDeleteComment,
                            onUpdateComment = onUpdateComment
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CommentCard(
    comment: CommentForPresentation,
    onDeleteComment: (String, String) -> Unit = { _, _ -> },
    onUpdateComment: (String, String, String) -> Unit = { _, _, _ -> }
) {
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditCancelDialog by remember { mutableStateOf(false) }
    val originalCommentContent = comment.content
    var commentContent by remember { mutableStateOf(comment.content) }

    val coroutineScope = rememberCoroutineScope()

    val swipeState = remember {
        AnchoredDraggableState(
            initialValue = SwipeState.Closed,
        )
    }

    SwipeableBox(
        swipeState = swipeState,
        swipeWidth = 96.dp,
        backgroundContent = {
            Row(
                Modifier
                    .matchParentSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { showDeleteDialog = true },
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Comment")
                }
                IconButton(
                    onClick = { showDialog = true },
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Comment")
                }
            }

        },
        content = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(12.dp)
                ) {
                    val authorText = comment.author ?: "Unknown Author"
                    Text(
                        text = authorText,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(comment.content, style = MaterialTheme.typography.bodyMedium)
                }
            }
            if (showDialog) {
                Dialog(
                    onDismissRequest = { showDialog = false }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)),
                        tonalElevation = 8.dp,
                        color = MaterialTheme.colorScheme.surface
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(12.dp)
                        ) {
                            val authorText = comment.author ?: "Unknown Author"
                            Text(
                                text = authorText,
                                style = MaterialTheme.typography.labelSmall
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            TextField(
                                value = commentContent,
                                onValueChange = { commentContent = it },
                                placeholder = { Text("댓글을 입력하세요") },
                                maxLines = 1,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                )
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {

                                TextButton(
                                    onClick = {
                                        if (originalCommentContent != commentContent) {
                                            showEditCancelDialog = true
                                        } else {
                                            showDialog = false
                                        }
                                    }
                                ) {
                                    Text("취소")
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                TextButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            swipeState.snapTo(SwipeState.Closed)
                                            kotlinx.coroutines.delay(150)
                                            onUpdateComment(
                                                comment.id ?: "",
                                                comment.projectId,
                                                commentContent
                                            )
                                        }

                                        showDialog = false
                                    }
                                ) {
                                    Text("수정")
                                }
                            }
                        }
                    }
                }

            }
        }
    )
    if (showDeleteDialog) {
        ConfirmDeleteDialog(
            targetName = comment.content,
            targetType = TargetType.COMMENT,
            onConfirm = {
                coroutineScope.launch {
                    swipeState.snapTo(SwipeState.Closed)
                    kotlinx.coroutines.delay(150)
                    onDeleteComment(comment.id ?: "", comment.projectId)
                }

                showDeleteDialog = false
            },
            onDismiss = {
                showDeleteDialog = false
            }
        )
    }

    if (showEditCancelDialog) {
        ConfirmEditCancelDialog(
            targetName = comment.content,
            targetType = TargetType.COMMENT,
            onConfirm = {
                coroutineScope.launch {
                    swipeState.snapTo(SwipeState.Closed)
                    kotlinx.coroutines.delay(150)
                }

                commentContent = originalCommentContent
                showEditCancelDialog = false
                showDialog = false
            },
            onDismiss = {
                coroutineScope.launch {
                    swipeState.snapTo(SwipeState.Closed)
                    kotlinx.coroutines.delay(150)
                }

                showEditCancelDialog = false
            }
        )
    }
}
