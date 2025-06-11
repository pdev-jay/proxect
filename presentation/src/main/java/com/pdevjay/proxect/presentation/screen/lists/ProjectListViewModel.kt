package com.pdevjay.proxect.presentation.screen.lists

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.usecase.GetFutureProjectsUseCase
import com.pdevjay.proxect.domain.usecase.GetPastProjectsUseCase
import com.pdevjay.proxect.domain.utils.toEpochMillis
import com.pdevjay.proxect.presentation.screen.calendar.component.toLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    private val getPastProjectsUseCase: GetPastProjectsUseCase,
    private val getFutureProjectsUseCase: GetFutureProjectsUseCase
) : ViewModel() {

    private val _visibleProjects = MutableStateFlow<List<Project>>(emptyList())
    val visibleProjects: StateFlow<List<Project>> = _visibleProjects

    private val _isLoadingPast = MutableStateFlow(false)
    val isLoadingPast: StateFlow<Boolean> = _isLoadingPast
    private val _isLoadingFuture = MutableStateFlow(false)
    val isLoadingFuture: StateFlow<Boolean> = _isLoadingFuture

    private var lastLoadedPastDate: LocalDate = LocalDate.now()
    private var lastLoadedFutureDate: LocalDate = LocalDate.now()

    private var isPastEndReached = false
    private var isFutureEndReached = false

    init {
        loadInitialProjects()
    }

    private fun loadInitialProjects() {
        if (_isLoadingPast.value || _isLoadingFuture.value) return

        viewModelScope.launch {
            _isLoadingPast.value = true

            val projects = getFutureProjectsUseCase(LocalDate.now())
            Log.e("projects", "${LocalDate.now().toEpochMillis()}")
            Log.e("projects", "${LocalDate.now()}")
            _visibleProjects.value = projects
//            _visibleProjects.value = projects.sortedBy { it.startDate }
            Log.e("projects", "${projects}")
            if (projects.isNotEmpty()) {
                lastLoadedFutureDate = projects.last().startDate.toLocalDate()
            } else {
            }
            _isLoadingPast.value = false
        }
    }

    fun loadMoreFutureProjects() {
        if (_isLoadingPast.value || _isLoadingFuture.value) return

        viewModelScope.launch {
            _isLoadingFuture.value = true

            val projects = getFutureProjectsUseCase(lastLoadedFutureDate)
            if (projects.isEmpty()) {
                isFutureEndReached = true
            } else {
                lastLoadedFutureDate = projects.last().startDate.toLocalDate()
                _visibleProjects.update { current ->
                    (current + projects).distinctBy { it.id }
//                        .sortedBy { it.startDate }
                }
            }

            _isLoadingFuture.value = false
        }
    }

    fun loadMorePastProjects() {
        if (_isLoadingPast.value || _isLoadingFuture.value) return

        viewModelScope.launch {
            _isLoadingPast.value = true

            val pastProjects = getPastProjectsUseCase(lastLoadedPastDate)
            if (pastProjects.isEmpty()) {
                isPastEndReached = true
            } else {
                lastLoadedPastDate = pastProjects.last().startDate.toLocalDate()
                _visibleProjects.update { current ->
                    (pastProjects + current).distinctBy { it.id }
//                        .sortedBy { it.startDate }
                }
            }

            _isLoadingPast.value = false
        }
    }
}
