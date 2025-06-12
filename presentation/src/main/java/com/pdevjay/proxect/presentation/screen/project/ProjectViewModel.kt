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
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val useCases: ProjectUseCases
) : ViewModel() {

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    private val _projectsForHome = MutableStateFlow<List<Project>>(emptyList())
    val projectsForHome: StateFlow<List<Project>> = _projectsForHome.asStateFlow()

    init{
        loadProjectsForHome()
    }

    fun loadProjects(firstDate: LocalDate, lastDate: LocalDate) {
        viewModelScope.launch {
            _projects.value = useCases.getProjects(firstDate, lastDate)
        }
    }
    fun loadProjectsForHome() {
        viewModelScope.launch {
            _projectsForHome.value = useCases.getProjectsForHome()
        }
    }

    fun addProject(project: Project) {
        viewModelScope.launch {
            useCases.insertProject(project)
            loadProjectsForHome()
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch {
            useCases.deleteProject(project.id)
            loadProjectsForHome()
        }
    }

    fun updateProject(project: Project) {
        viewModelScope.launch {
            useCases.updateProject(project)
            loadProjectsForHome()
        }
    }
}
