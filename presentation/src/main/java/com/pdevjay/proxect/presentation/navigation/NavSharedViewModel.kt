package com.pdevjay.proxect.presentation.navigation

import androidx.lifecycle.ViewModel
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NavSharedViewModel @Inject constructor() : ViewModel() {

    private val _selectedProject = MutableStateFlow<ProjectForPresentation?>(null)
    val selectedProject: StateFlow<ProjectForPresentation?> = _selectedProject.asStateFlow()

    private val _selectedProjects = MutableStateFlow<List<ProjectForPresentation>>(emptyList())
    val selectedProjects: StateFlow<List<ProjectForPresentation>> = _selectedProjects.asStateFlow()

    fun setProject(project: ProjectForPresentation) {
        _selectedProject.value = project
    }

    fun setProjects(projects: List<ProjectForPresentation>) {
        _selectedProjects.value = projects
    }
}
