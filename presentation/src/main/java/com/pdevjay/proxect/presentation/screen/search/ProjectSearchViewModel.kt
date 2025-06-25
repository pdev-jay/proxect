package com.pdevjay.proxect.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdevjay.proxect.domain.common.UseCaseResult
import com.pdevjay.proxect.domain.common.handleUseCaseResult
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.model.ProjectStatus
import com.pdevjay.proxect.domain.usecase.ProjectUseCases
import com.pdevjay.proxect.domain.utils.toEpochMillis
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.data.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class ProjectSearchViewModel @Inject constructor(
    private val useCases: ProjectUseCases
) : ViewModel() {
    private val _searchedProjects = MutableStateFlow<List<ProjectForPresentation>>(emptyList())
    val searchedProjects: MutableStateFlow<List<ProjectForPresentation>> = _searchedProjects

    private val _searchState: MutableStateFlow<SearchState> = MutableStateFlow(
        SearchState(
            startDate = LocalDate.now().minusWeeks(2).toEpochMillis(),
            endDate = LocalDate.now().plusWeeks(2).toEpochMillis()
        )
    )
    val searchState: MutableStateFlow<SearchState> = _searchState

    fun searchProjectsWithDates(
        startDate: LocalDate,
        endDate: LocalDate,
        onSuccess: () -> Unit,
        onFailure: (message: String, throwable: Throwable?) -> Unit,
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {
            flow {
                try {
                    val projects =
                        useCases.getProjects(startDate, endDate)
                            .sortedWith(compareBy<Project> { it.startDate }.thenBy { it.id })
                            .map { it.toPresentation() }
                    _searchedProjects.value = projects
                    emit(UseCaseResult.Success(Unit))
                } catch (e: Exception) {
                    emit(UseCaseResult.Failure("프로젝트 검색 실패", e))
                }
            }.handleUseCaseResult(
                onSuccess = { onSuccess() },
                onFailure = { error -> onFailure(error.message ?: "", error.throwable) },
                onComplete = onComplete
            )
        }
    }

    fun updateSearchState(
        isStatusFilterActive: Boolean? = null,
        isDateFilterActive: Boolean? = null,
        projectStatus: ProjectStatus? = null,
        startDate: Long? = null,
        endDate: Long? = null,
        searchQuery: String? = null
    ) {
        val current = _searchState.value
        val updated = current.copy(
            isStatusFilterActive = isStatusFilterActive ?: current.isStatusFilterActive,
            isDateFilterActive = isDateFilterActive ?: current.isDateFilterActive,
            selectedStatus = projectStatus ?: current.selectedStatus,
            startDate = startDate ?: current.startDate,
            endDate = endDate ?: current.endDate,
            searchQuery = searchQuery ?: current.searchQuery
        )

        if (updated != current) {
            _searchState.value = updated
            triggerSearch()
        }
    }

    private fun triggerSearch() {
        val state = _searchState.value
        val status = if (state.isStatusFilterActive) state.selectedStatus else null
        val start = if (state.isDateFilterActive) state.startDate else null
        val end = if (state.isDateFilterActive) state.endDate else null

        searchProjectsWithFilters(
            query = state.searchQuery,
            status = status,
            startDate = start,
            endDate = end
        )
    }

    fun searchProjectsWithFilters(
        query: String,
        status: ProjectStatus?,
        startDate: Long?,
        endDate: Long?
    ) {
        viewModelScope.launch {
            flow {
                try {
                    val projects =
                        useCases.searchProjects(
                            query = query,
                            isStatusFilterActive = status != null,
                            status = status,
                            isDateFilterActive = startDate != null && endDate != null,
                            startDate = startDate,
                            endDate = endDate
                        )
                            ?.sortedWith(compareBy<Project> { it.startDate }.thenBy { it.id })
                            ?.map { it.toPresentation() }
                            ?: _searchedProjects.value
                    if (projects != _searchedProjects.value) {
                        _searchedProjects.value = projects
                    }
                    emit(UseCaseResult.Success(Unit))
                } catch (e: Exception) {
                    emit(UseCaseResult.Failure("프로젝트 검색 실패", e))
                }
            }.handleUseCaseResult(
                onSuccess = {},
                onFailure = { error ->
                    _searchedProjects.value = emptyList()
                },
                onComplete = {}
            )
        }
    }
}