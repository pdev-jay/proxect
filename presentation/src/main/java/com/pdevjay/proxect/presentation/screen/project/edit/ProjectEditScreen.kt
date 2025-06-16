package com.pdevjay.proxect.presentation.screen.project.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pdevjay.proxect.presentation.data.ProjectForPresentation

@Composable
fun ProjectEditScreen(
    navController: NavController,
    project: ProjectForPresentation
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Edit Project Screen")
    }
}