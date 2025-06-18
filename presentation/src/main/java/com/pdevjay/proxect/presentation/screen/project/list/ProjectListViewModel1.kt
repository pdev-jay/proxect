package com.pdevjay.proxect.presentation.screen.project.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import javax.inject.Inject

class ProjectListViewModel1 @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val projectList: List<ProjectForPresentation>? = savedStateHandle.get("project_list")
}