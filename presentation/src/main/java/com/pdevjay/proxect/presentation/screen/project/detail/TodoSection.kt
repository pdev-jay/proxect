package com.pdevjay.proxect.presentation.screen.project.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.pdevjay.proxect.presentation.data.TodoForPresentation

@Composable
fun TodoSection(
    todos: List<TodoForPresentation>,
    onAddTodo: (String) -> Unit,
    onDeleteTodo: (String, String) -> Unit = { _, _ -> },
    onUpdateTodo: (String, String, String) -> Unit = { _, _, _ -> },
    onDismiss: () -> Unit = {}
) {
    var todoText by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(
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
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = todoText,
                    onValueChange = { todoText = it },
                    placeholder = { Text("할일을 입력하세요") },
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            if (todoText.isNotBlank()) {
                                onAddTodo(todoText.trim())
                                todoText = ""
                            }
                        }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add Todo")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(todos) {
                        TodoCard(
                            it,
                            onDeleteTodo = onDeleteTodo,
                            onUpdateTodo = onUpdateTodo
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
    onUpdateTodo: (String, String, String) -> Unit = { _, _, _ -> }
) {
    val originalTodoContent = todo.title
    var todoContent by remember { mutableStateOf(todo.title) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditCancelDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(todo.title, style = MaterialTheme.typography.bodyMedium)
        IconButton(
            onClick = { onDeleteTodo(todo.id!!, todo.projectId) },
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Todo")
        }
    }

}