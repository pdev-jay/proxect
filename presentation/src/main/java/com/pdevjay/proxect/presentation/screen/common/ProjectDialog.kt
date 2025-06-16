package com.pdevjay.proxect.presentation.screen.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdevjay.proxect.domain.utils.toUTCLocalDate
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.data.toEditNav
import com.pdevjay.proxect.presentation.screen.calendar.model.DialogContentType
import com.pdevjay.proxect.presentation.screen.project.ProjectEditContent
import java.time.LocalDate

@Composable
fun ProjectDialog(
    navController: NavController,
    initialContentType: DialogContentType? = DialogContentType.ProjectList,
    selectedDate: LocalDate,
    initialSelectedProject: ProjectForPresentation? = null,
    projects: List<ProjectForPresentation>? = null,
    onDismiss: () -> Unit,
    onDelete: (ProjectForPresentation) -> Unit = {},
    onUpdate: (ProjectForPresentation) -> Unit = {}
) {
    var contentType by remember {
        mutableStateOf<DialogContentType>(
            initialContentType ?: DialogContentType.ProjectList
        )
    }
    var selectedProject by remember { mutableStateOf<ProjectForPresentation?>(initialSelectedProject) }
    var editedProject by remember { mutableStateOf<ProjectForPresentation?>(selectedProject?.copy()) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var showUpdateConfirmDialog by remember { mutableStateOf(false) }
    var showUpdateCancelDialog by remember { mutableStateOf(false) }

    DialogTemplate(
        onDismissRequest = {
            if (contentType == DialogContentType.ProjectDetail && projects != null) {
                contentType = DialogContentType.ProjectList
            }
            onDismiss()
        },
        contentType = contentType,
        titleContent = {
            ProjectDialogHeader(
                contentType = contentType,
                selectedDate = selectedDate,
                showBack = projects != null,
                onDismiss = {
                    if (contentType == DialogContentType.ProjectDetail) {
                        contentType = DialogContentType.ProjectList
                    }
                    onDismiss()
                },
                onEdit = {
                    if (selectedProject != null) {
                        navController.navigate(
                            selectedProject!!.toEditNav()
                        )
                    }
                    onDismiss()
//                    contentType = DialogContentType.EditProject

                },
                onConfirmEdit = {
                    showUpdateConfirmDialog = true
                },
                onBack = {
                    if (contentType == DialogContentType.EditProject) {
                        if (selectedProject != editedProject) {
                            showUpdateCancelDialog = true
                        } else {
                            editedProject = selectedProject?.copy()
                            contentType = DialogContentType.ProjectDetail
                        }
                    } else if (contentType == DialogContentType.ProjectDetail) {
                        if (projects != null) {
                            contentType = DialogContentType.ProjectList
                        }
                    }
                }
            )
        },
        content = { content ->
            when (content) {
                DialogContentType.ProjectList -> {
                    if (projects != null) {
                        for (project in projects) {
                            ProjectCard(
                                project,
                                onClick = {
                                    selectedProject = project
                                    editedProject = project.copy()
                                    contentType = DialogContentType.ProjectDetail
                                }
                            )
                        }
                    }
                }

                DialogContentType.ProjectDetail -> {
                    selectedProject?.let { project ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("프로젝트", style = MaterialTheme.typography.titleMedium)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(project.name)
                                Text(
                                    "${project.status.displayName}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            HorizontalDivider(color = Color.LightGray)
                            Spacer(modifier = Modifier)
                            Text("기간", style = MaterialTheme.typography.titleMedium)
                            Text("${project.startDate.toUTCLocalDate()} - ${project.endDate.toUTCLocalDate()}")
                            HorizontalDivider(color = Color.LightGray)
                            Spacer(modifier = Modifier)
                            Text(project.description)
                        }
                    }
                }

                DialogContentType.EditProject -> {
                    ProjectEditContent(
                        project = editedProject!!,
                        onChange = { changedProject ->
                            editedProject = changedProject
                        },
                    )
                }

                DialogContentType.AddProject -> {}
            }
        },
        bottomContent = {
            if (contentType == DialogContentType.ProjectDetail) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {
                        showDeleteConfirmDialog = true
                    }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "삭제"
                        )
                    }
                }
            }
        }
    )

    // 삭제 확인 다이얼로그
    if (showDeleteConfirmDialog && selectedProject != null) {
        ConfirmDeleteDialog(
            projectName = selectedProject!!.name,
            onConfirm = {
                onDelete(selectedProject!!)
                showDeleteConfirmDialog = false
                if (projects != null) {
                    contentType = DialogContentType.ProjectList
                } else {
                    onDismiss()
                }
            },
            onDismiss = {
                showDeleteConfirmDialog = false
            }
        )
    }

    if (showUpdateConfirmDialog && selectedProject != null) {
        ConfirmEditDialog(
            projectName = selectedProject!!.name,
            onConfirm = {
                if (selectedProject != editedProject) {
                    selectedProject = editedProject!!
                    editedProject = editedProject!!.copy()
                    onUpdate(editedProject!!)
                }
                showUpdateConfirmDialog = false
                contentType = DialogContentType.ProjectDetail
            },
            onDismiss = {
                editedProject = selectedProject!!.copy()
                showUpdateConfirmDialog = false
            }
        )
    }

    if (showUpdateCancelDialog && selectedProject != null) {
        ConfirmEditCancelDialog(
            projectName = selectedProject!!.name,
            onConfirm = {
                editedProject = selectedProject!!.copy()
                showUpdateCancelDialog = false
                contentType = DialogContentType.ProjectDetail
            },
            onDismiss = {
                showUpdateCancelDialog = false
            }
        )
    }
}
//@Composable
//fun ProjectDialog(
//    initialContentType: DialogContentType? = DialogContentType.ProjectList,
//    selectedDate: LocalDate,
//    initialSelectedProject: Project? = null,
//    projects: List<Project>? = null,
//    onDismiss: () -> Unit,
//    onDelete: (Project) -> Unit = {},
//    onUpdate: (Project) -> Unit = {}
//) {
//    var contentType by remember {
//        mutableStateOf<DialogContentType>(
//            initialContentType ?: DialogContentType.ProjectList
//        )
//    }
//    var selectedProject by remember { mutableStateOf<Project?>(initialSelectedProject) }
//    var editedProject by remember { mutableStateOf<Project?>(selectedProject?.copy()) }
//    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
//    var showUpdateConfirmDialog by remember { mutableStateOf(false) }
//    var showUpdateCancelDialog by remember { mutableStateOf(false) }
//
//    Dialog(properties = DialogProperties(
//        usePlatformDefaultWidth = false,
//        dismissOnBackPress = true
//    ),
//        onDismissRequest = {
//            if (contentType == DialogContentType.ProjectDetail && projects != null) {
//                contentType = DialogContentType.ProjectList
//            }
//            onDismiss()
//        }) {
//
//        if (contentType == DialogContentType.ProjectDetail && projects != null) {
//            BackHandler {
//                contentType = DialogContentType.ProjectList
//            }
//        }
//
//        Surface(
//            modifier = Modifier
//                .fillMaxWidth(0.9f)
//                .fillMaxHeight(0.85f) // 화면 90% 덮기 (원하면 .fillMaxSize()도 가능)
//                .clip(RoundedCornerShape(16.dp)),
//            tonalElevation = 8.dp,
//            shape = MaterialTheme.shapes.large,
//            color = MaterialTheme.colorScheme.surface
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                ProjectDialogHeader(
//                    contentType = contentType,
//                    selectedDate = selectedDate,
//                    showBack = projects != null,
//                    onDismiss = {
//                        if (contentType == DialogContentType.ProjectDetail) {
//                            contentType = DialogContentType.ProjectList
//                        }
//                        onDismiss()
//                    },
//                    onEdit = {
//                        contentType = DialogContentType.EditProject
//                    },
//                    onConfirmEdit = {
//                        showUpdateConfirmDialog = true
//                    },
//                    onBack = {
//                        if (contentType == DialogContentType.EditProject) {
//                            if (selectedProject != editedProject){
//                                showUpdateCancelDialog = true
//                            } else {
//                                editedProject = selectedProject?.copy()
//                                contentType = DialogContentType.ProjectDetail
//                            }
//                        } else if (contentType == DialogContentType.ProjectDetail) {
//                            if (projects != null) {
//                                contentType = DialogContentType.ProjectList
//                            }
//                        }
//                    }
//                )
//
//                AnimatedContent(
//                    targetState = contentType,
//                    transitionSpec = {
//                        when {
//                            initialState == DialogContentType.ProjectList && targetState == DialogContentType.ProjectDetail ||
//                                    initialState == DialogContentType.ProjectDetail && targetState == DialogContentType.EditProject -> {
//                                // 앞으로 이동: 오른쪽에서 들어오고 왼쪽으로 나감
//                                slideInHorizontally { it } + fadeIn() togetherWith
//                                        slideOutHorizontally { -it } + fadeOut()
//                            }
//
//                            initialState == DialogContentType.EditProject && targetState == DialogContentType.ProjectDetail ||
//                                    initialState == DialogContentType.ProjectDetail && targetState == DialogContentType.ProjectList -> {
//                                // 뒤로 이동: 왼쪽에서 들어오고 오른쪽으로 나감
//                                slideInHorizontally { -it } + fadeIn() togetherWith
//                                        slideOutHorizontally { it } + fadeOut()
//                            }
//
//                            else -> {
//                                // 기본 fallback (crossfade)
//                                fadeIn() togetherWith fadeOut()
//                            }
//                        }.using(SizeTransform(clip = false))
//                    },
//                    label = "DialogContentSlide"
//                ) { content ->
//
//                    when (content) {
//                        DialogContentType.ProjectList -> {
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .verticalScroll(rememberScrollState())
//
//                            ) {
//                                if (projects != null) {
//                                    for (project in projects) {
//                                        ProjectCard(
//                                            project,
//                                            onClick = {
//                                                selectedProject = project
//                                                editedProject = project.copy()
//                                                contentType = DialogContentType.ProjectDetail
//                                            }
//                                        )
//                                    }
//                                }
//                            }
//                        }
//
//                        DialogContentType.ProjectDetail -> {
//                            selectedProject?.let { project ->
//                                Column(
//                                    modifier = Modifier
//                                        .fillMaxHeight()
//                                        .verticalScroll(rememberScrollState()),
//                                    verticalArrangement = Arrangement.SpaceBetween
//                                ) {
//                                    Column(
//                                        verticalArrangement = Arrangement.spacedBy(8.dp)
//                                    ) {
//                                        Text("프로젝트", style = MaterialTheme.typography.titleMedium)
//                                        Text(project.name)
//                                        HorizontalDivider(color = Color.LightGray)
//                                        Spacer(modifier = Modifier)
//                                        Text("기간", style = MaterialTheme.typography.titleMedium)
//                                        Text("${project.startDate.toUTCLocalDate()} - ${project.endDate.toUTCLocalDate()}")
//                                        HorizontalDivider(color = Color.LightGray)
//                                        Spacer(modifier = Modifier)
//                                        Text(project.description)
//                                    }
//
//                                    Row(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        horizontalArrangement = Arrangement.End
//                                    ) {
//                                        IconButton(onClick = {
//                                            showDeleteConfirmDialog = true
//                                        }) {
//                                            Icon(
//                                                Icons.Default.Delete,
//                                                contentDescription = "삭제"
//                                            )
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                        DialogContentType.EditProject -> {
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxHeight()
//                                    .verticalScroll(rememberScrollState()),
//                                verticalArrangement = Arrangement.SpaceBetween
//                            ) {
//
//                                ProjectEditContent(
//                                    project = editedProject!!,
//                                    onChange = { changedProject ->
//                                        editedProject = changedProject
//                                    },
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    // 삭제 확인 다이얼로그
//    if (showDeleteConfirmDialog && selectedProject != null) {
//        ConfirmDeleteDialog(
//            projectName = selectedProject!!.name,
//            onConfirm = {
//                onDelete(selectedProject!!)
//                showDeleteConfirmDialog = false
//                if (projects != null) {
//                    contentType = DialogContentType.ProjectList
//                } else {
//                    onDismiss()
//                }
//            },
//            onDismiss = {
//                showDeleteConfirmDialog = false
//            }
//        )
//    }
//
//    if (showUpdateConfirmDialog && selectedProject != null) {
//        ConfirmEditDialog(
//            projectName = selectedProject!!.name,
//            onConfirm = {
//                if (selectedProject != editedProject){
//                    selectedProject = editedProject!!
//                    editedProject = editedProject!!.copy()
//                    onUpdate(editedProject!!)
//                }
//                showUpdateConfirmDialog = false
//                contentType = DialogContentType.ProjectDetail
//            },
//            onDismiss = {
//                editedProject = selectedProject!!.copy()
//                showUpdateConfirmDialog = false
//            }
//        )
//    }
//
//    if (showUpdateCancelDialog && selectedProject != null) {
//        ConfirmEditCancelDialog(
//            projectName = selectedProject!!.name,
//            onConfirm = {
//                editedProject = selectedProject!!.copy()
//                showUpdateCancelDialog = false
//                contentType = DialogContentType.ProjectDetail
//            },
//            onDismiss = {
//                showUpdateCancelDialog = false
//            }
//        )
//    }
//}


@Composable
fun ConfirmDeleteDialog(
    projectName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("프로젝트 삭제") },
        text = { Text("\"$projectName\" 프로젝트를 정말 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("삭제", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}

@Composable
fun ConfirmEditDialog(
    projectName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("프로젝트 수정") },
        text = {
            Text("\"$projectName\" 프로젝트의 변경 사항을 저장하시겠습니까?")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("저장", color = MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}

@Composable
fun ConfirmEditCancelDialog(
    projectName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("프로젝트 수정 취소") },
        text = {
            Text("\"$projectName\" 프로젝트의 변경 사항을 취소하시겠습니까?")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("확인", color = MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}
