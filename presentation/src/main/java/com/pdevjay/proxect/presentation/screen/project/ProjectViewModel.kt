package com.pdevjay.proxect.presentation.screen.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.usecase.ProjectUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val useCases: ProjectUseCases
) : ViewModel() {

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    init{
        loadProjects()
    }

    fun loadProjects() {
        viewModelScope.launch {
            _projects.value = useCases.getProjects()
        }
    }

    fun addProject(project: Project) {
        viewModelScope.launch {
            useCases.insertProject(project)
            loadProjects()
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch {
            useCases.deleteProject(project.id)
            loadProjects()
        }
    }

    fun updateProject(project: Project) {
        viewModelScope.launch {
            useCases.updateProject(project)
            loadProjects()
        }
    }
}
