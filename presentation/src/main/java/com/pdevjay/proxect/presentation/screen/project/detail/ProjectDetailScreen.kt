package com.pdevjay.proxect.presentation.screen.project.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.pdevjay.proxect.presentation.data.ProjectForPresentation


@Composable
fun ProjectDetailScreen(
    navController: NavController,
    project: ProjectForPresentation
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = "Detail Project Screen")
        Text(text = "${project.name}")
    }
}