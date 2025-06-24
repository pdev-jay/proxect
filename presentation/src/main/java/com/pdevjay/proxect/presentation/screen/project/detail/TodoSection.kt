package com.pdevjay.proxect.presentation.screen.project.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pdevjay.proxect.presentation.data.TodoForPresentation
import com.pdevjay.proxect.presentation.screen.project.component.ConfirmDeleteDialog
import com.pdevjay.proxect.presentation.screen.project.component.ConfirmEditCancelDialog
import com.pdevjay.proxect.presentation.screen.project.component.TargetType
import kotlinx.coroutines.launch

@Composable
fun TodoSection(
    todos: List<TodoForPresentation>,
    onAddTodo: (String) -> Unit,
    onDeleteTodo: (String, String) -> Unit = { _, _ -> },
    onUpdateTodo: (String, String, String, Boolean) -> Unit = { _, _, _, _ -> },
    onDismiss: () -> Unit = {}
) {
    var todoText by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = "할 일",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close Dialog")
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = todoText,
                        onValueChange = { todoText = it },
                        placeholder = { Text("할 일을 입력하세요") },
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
                        if (todoText.isNotBlank()) {
                            onAddTodo(todoText.trim())
                            todoText = ""
                        }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Todo")
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(todos) {
                        TodoCard(
                            it, onDeleteTodo = onDeleteTodo, onUpdateTodo = onUpdateTodo
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TodoCard(
    todo: TodoForPresentation,
    onDeleteTodo: (String, String) -> Unit = { _, _ -> },
    onUpdateTodo: (String, String, String, Boolean) -> Unit = { _, _, _, _ -> }
) {
    val coroutineScope = rememberCoroutineScope()

    val originalTodoContent = todo.title
    var todoContent by remember { mutableStateOf(todo.title) }
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditCancelDialog by remember { mutableStateOf(false) }

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
                    modifier = Modifier.size(48.dp),
                    onClick = { showDeleteDialog = true },
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Comment")
                }
                IconButton(
                    modifier = Modifier.size(48.dp),
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
                Checkbox(checked = todo.isDone, onCheckedChange = {
                    onUpdateTodo(todo.projectId, todo.id!!, todoContent, it)
                })
                AnimatedContent(
                    modifier = Modifier.weight(1f),
                    targetState = todo.isDone,
                    label = "TodoCrossfade",
                    transitionSpec = {
                        fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                    },
                    contentAlignment = AbsoluteAlignment.CenterLeft
                ) { done ->
                    Text(
                        text = todo.title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            textDecoration = if (done) TextDecoration.LineThrough else null
                        ),
                        color = if (done) Color.Gray else LocalContentColor.current
                    )
                }
            }

            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)),
                        tonalElevation = 8.dp,
                        color = MaterialTheme.colorScheme.surface
                    ) {

                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            TextField(
                                value = todoContent,
                                onValueChange = { todoContent = it },
                                placeholder = { Text("할 일을 입력하세요") },
                                maxLines = 1,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                )
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {

                                TextButton(onClick = {
                                    if (originalTodoContent != todoContent) {
                                        showEditCancelDialog = true
                                    } else {
                                        showDialog = false
                                    }
                                }) {
                                    Text("취소")
                                }
                                Spacer(modifier = Modifier.weight(1f))
//                                }
                                TextButton(onClick = {
                                    coroutineScope.launch {
                                        swipeState.snapTo(SwipeState.Closed)
                                        kotlinx.coroutines.delay(150)
                                        onUpdateTodo(
                                            todo.projectId, todo.id!!, todoContent, todo.isDone
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
        ConfirmDeleteDialog(targetName = todo.title, targetType = TargetType.TODO, onConfirm = {
            coroutineScope.launch {
                swipeState.snapTo(SwipeState.Closed)
                kotlinx.coroutines.delay(150)
                onDeleteTodo(todo.id!!, todo.projectId)
            }
            showDeleteDialog = false
        }, onDismiss = {
            coroutineScope.launch {
                swipeState.snapTo(SwipeState.Closed)
                kotlinx.coroutines.delay(150)
            }
            showDeleteDialog = false
        })
    }

    if (showEditCancelDialog) {
        ConfirmEditCancelDialog(targetName = todo.title, targetType = TargetType.TODO, onConfirm = {
            coroutineScope.launch {
                swipeState.snapTo(SwipeState.Closed)
                kotlinx.coroutines.delay(150)
            }
            todoContent = originalTodoContent
            showEditCancelDialog = false
            showDialog = false
        }, onDismiss = {
            coroutineScope.launch {
                swipeState.snapTo(SwipeState.Closed)
                kotlinx.coroutines.delay(150)
            }
            showEditCancelDialog = false
        }
        )
    }
}
