package com.pdevjay.proxect.presentation.screen.lists

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdevjay.proxect.domain.usecase.ProjectUseCases
import com.pdevjay.proxect.domain.utils.toEpochMillis
import com.pdevjay.proxect.domain.utils.toUTCLocalDate
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.data.toDomain
import com.pdevjay.proxect.presentation.data.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    private val useCases: ProjectUseCases
) : ViewModel() {

    private val _visibleProjects = MutableStateFlow<List<ProjectForPresentation>>(emptyList())
    val visibleProjects: StateFlow<List<ProjectForPresentation>> = _visibleProjects

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

            val projects = useCases.getFutureProjects(LocalDate.now()).map { it.toPresentation() }
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

            val projects = useCases.getFutureProjects(
                lastLoadedFutureDate,
                if (_visibleProjects.value.isNotEmpty()) _visibleProjects.value.last().id else null
            )
                .map { it.toPresentation() }
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

            val pastProjects = useCases.getPastProjects(
                lastLoadedPastDate,
                if (_visibleProjects.value.isNotEmpty()) _visibleProjects.value.first().id else null
            )
                .map { it.toPresentation() }
            if (pastProjects.isEmpty()) {
            } else {
                lastLoadedPastDate = pastProjects.first().startDate.toUTCLocalDate()
                val sortedPastProjects =
                    pastProjects.sortedWith(compareBy<ProjectForPresentation> { it.startDate }.thenBy { it.id })
                _visibleProjects.update { current ->
                    (sortedPastProjects + current).distinctBy { it.id }
                        .sortedBy { it.startDate }
                }
            }
            _isLoadingPast.value = false
        }
    }

    fun deleteProject(project: ProjectForPresentation) {
        viewModelScope.launch {
            useCases.deleteProject(project.toDomain().id)
            refreshProjects()
        }
    }

    fun updateProject(project: ProjectForPresentation) {
        viewModelScope.launch {
            useCases.updateProject(project.toDomain())
            refreshProjects()
        }
    }

    fun refreshProjects() {
        viewModelScope.launch {
            _isLoadingPast.value = true
            _isLoadingFuture.value = true

            val projects = useCases.getProjects(lastLoadedPastDate, lastLoadedFutureDate)
                .map { it.toPresentation() }
            _visibleProjects.value =
                projects.sortedWith(compareBy<ProjectForPresentation> { it.startDate }.thenBy { it.id })

            _isLoadingPast.value = false
            _isLoadingFuture.value = false
        }
    }
}
