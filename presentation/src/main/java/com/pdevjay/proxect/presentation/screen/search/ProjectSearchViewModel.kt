package com.pdevjay.proxect.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdevjay.proxect.domain.common.UseCaseResult
import com.pdevjay.proxect.domain.common.handleUseCaseResult
import com.pdevjay.proxect.domain.usecase.ProjectUseCases
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
                        useCases.getProjects(startDate, endDate).map { it.toPresentation() }
                            .sortedBy { it.startDate }
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
}