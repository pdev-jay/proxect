package com.pdevjay.proxect.presentation.screen.lists

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.usecase.GetFutureProjectsUseCase
import com.pdevjay.proxect.domain.usecase.GetPastProjectsUseCase
import com.pdevjay.proxect.domain.usecase.GetProjectsUseCase
import com.pdevjay.proxect.domain.usecase.ProjectUseCases
import com.pdevjay.proxect.domain.utils.toEpochMillis
import com.pdevjay.proxect.domain.utils.toUTCLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    private val useCases: ProjectUseCases
) : ViewModel() {

    private val _visibleProjects = MutableStateFlow<List<Project>>(emptyList())
    val visibleProjects: StateFlow<List<Project>> = _visibleProjects

    private val _isLoadingPast = MutableStateFlow(false)
    val isLoadingPast: StateFlow<Boolean> = _isLoadingPast
    private val _isLoadingFuture = MutableStateFlow(false)
    val isLoadingFuture: StateFlow<Boolean> = _isLoadingFuture

    private var lastLoadedPastDate: LocalDate = LocalDate.now()
    private var lastLoadedFutureDate: LocalDate = LocalDate.now()

    init {
        loadInitialProjects()
    }

    private fun loadInitialProjects() {
        if (_isLoadingPast.value || _isLoadingFuture.value) return

        viewModelScope.launch {
            _isLoadingPast.value = true

            val projects = useCases.getFutureProjects(LocalDate.now())
            Log.e("projects", "${LocalDate.now().toEpochMillis()}")
            Log.e("projects", "${LocalDate.now()}")
            _visibleProjects.value = projects
//            _visibleProjects.value = projects.sortedBy { it.startDate }
            Log.e("projects", "${projects}")
            if (projects.isNotEmpty()) {
                lastLoadedFutureDate = projects.last().startDate.toUTCLocalDate()
            } else {
            }
            _isLoadingPast.value = false
        }
    }

    fun loadMoreFutureProjects() {
        if (_isLoadingPast.value || _isLoadingFuture.value) return

        viewModelScope.launch {
            _isLoadingFuture.value = true

            val projects = useCases.getFutureProjects(lastLoadedFutureDate,if(_visibleProjects.value.isNotEmpty()) _visibleProjects.value.last().id else null)
            if (projects.isEmpty()) {
            } else {
                lastLoadedFutureDate = projects.last().startDate.toUTCLocalDate()
                _visibleProjects.update { current ->
                    (current + projects).distinctBy { it.id }
                        .sortedBy { it.startDate }
                }
            }

            _isLoadingFuture.value = false
        }
    }

    fun loadMorePastProjects() {
        if (_isLoadingPast.value || _isLoadingFuture.value) return

        viewModelScope.launch {
            _isLoadingPast.value = true

            val pastProjects = useCases.getPastProjects(lastLoadedPastDate,if(_visibleProjects.value.isNotEmpty()) _visibleProjects.value.first().id else null)
            if (pastProjects.isEmpty()) {
            } else {
                lastLoadedPastDate = pastProjects.first().startDate.toUTCLocalDate()
                val sortedPastProjects = pastProjects.sortedWith(compareBy<Project> { it.startDate }.thenBy { it.id })
                _visibleProjects.update { current ->
                    (sortedPastProjects + current).distinctBy { it.id }
                        .sortedBy { it.startDate }
                }
            }
            _isLoadingPast.value = false
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch {
            useCases.deleteProject(project.id)
            refreshProjects()
        }
    }

    fun updateProject(project: Project) {
        viewModelScope.launch {
            useCases.updateProject(project)
            refreshProjects()
        }
    }

    fun refreshProjects() {
        viewModelScope.launch {
            _isLoadingPast.value = true
            _isLoadingFuture.value = true

            val projects = useCases.getProjects(lastLoadedPastDate, lastLoadedFutureDate)
            _visibleProjects.value = projects.sortedWith(compareBy<Project> { it.startDate }.thenBy { it.id })

            _isLoadingPast.value = false
            _isLoadingFuture.value = false
        }
    }
}
