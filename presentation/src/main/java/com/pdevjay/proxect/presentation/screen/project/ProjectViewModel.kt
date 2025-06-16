package com.pdevjay.proxect.presentation.screen.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.usecase.ProjectUseCases
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.data.toDomain
import com.pdevjay.proxect.presentation.data.toPresentation
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

    private val _projects = MutableStateFlow<List<ProjectForPresentation>>(emptyList())
    val projects: StateFlow<List<ProjectForPresentation>> = _projects.asStateFlow()

    private val _projectsForHome = MutableStateFlow<List<ProjectForPresentation>>(emptyList())
    val projectsForHome: StateFlow<List<ProjectForPresentation>> = _projectsForHome.asStateFlow()

    init {
        getAlllProjects()
//        loadProjectsForHome()
    }

    fun getAlllProjects() {
        viewModelScope.launch {
            val projects = useCases.getAllProjects()
                .sortedWith(compareBy<Project> { it.startDate }.thenBy { it.id })
                .map { it.toPresentation() }
            _projects.value = projects
        }
    }

    fun loadProjects(firstDate: LocalDate, lastDate: LocalDate) {
        viewModelScope.launch {
            _projects.value = useCases.getProjects(firstDate, lastDate)
                .map { it.toPresentation() }
        }
    }

    fun loadProjectsForHome() {
        viewModelScope.launch {
            _projectsForHome.value = useCases.getProjectsForHome()
                .map { it.toPresentation() }
        }
    }

    fun addProject(project: ProjectForPresentation) {
        viewModelScope.launch {
            useCases.insertProject(project.toDomain())
            getAlllProjects()
//            loadProjectsForHome()
        }
    }

    fun deleteProject(project: ProjectForPresentation) {
        viewModelScope.launch {
            useCases.deleteProject(project.toDomain().id)
            getAlllProjects()
//            loadProjectsForHome()
        }
    }

    fun updateProject(project: ProjectForPresentation) {
        viewModelScope.launch {
            useCases.updateProject(project.toDomain())
            getAlllProjects()
//            loadProjectsForHome()
        }
    }
}
