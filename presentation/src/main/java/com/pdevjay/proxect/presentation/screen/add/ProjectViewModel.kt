package com.pdevjay.proxect.presentation.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.usecase.GetProjectsUseCase
import com.pdevjay.proxect.domain.usecase.InsertProjectUseCase
import com.pdevjay.proxect.domain.usecase.ProjectUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val useCases: ProjectUseCases
) : ViewModel() {

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    fun loadProjects() {
        viewModelScope.launch {
            _projects.value = useCases.getProjects()
        }
    }

    fun addProject(title: String) {
        val newProject = Project(
            id = UUID.randomUUID().toString(),
            name = title,
            description = "", // 간단히 비워두기
            startDate = System.currentTimeMillis(),
            endDate = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000 // 일주일 뒤
        )

        viewModelScope.launch {
            useCases.insertProject(newProject)
            loadProjects()
        }
    }
}
