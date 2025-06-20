package com.pdevjay.proxect.presentation.screen.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdevjay.proxect.domain.common.UseCaseResult
import com.pdevjay.proxect.domain.common.handleUseCaseResult
import com.pdevjay.proxect.domain.model.Project
import com.pdevjay.proxect.domain.usecase.ProjectUseCases
import com.pdevjay.proxect.presentation.data.CommentForPresentation
import com.pdevjay.proxect.presentation.data.ProjectForPresentation
import com.pdevjay.proxect.presentation.data.toDomain
import com.pdevjay.proxect.presentation.data.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val useCases: ProjectUseCases
) : ViewModel() {

    private val _projects = MutableStateFlow<List<ProjectForPresentation>>(emptyList())
    val projects: StateFlow<List<ProjectForPresentation>> = _projects.asStateFlow()

    private val _comments = MutableStateFlow<List<CommentForPresentation>>(emptyList())
    val comments: StateFlow<List<CommentForPresentation>> = _comments.asStateFlow()

    init {
        viewModelScope.launch {
            getAlllProjects()
        }
    }

    suspend fun getAlllProjects(): UseCaseResult<Unit> {
        return try {
            val projects = useCases.getAllProjects()
                .sortedWith(compareBy<Project> { it.startDate }.thenBy { it.id })
                .map { it.toPresentation() }
            _projects.value = projects
            UseCaseResult.Success(Unit)
        } catch (e: Exception) {
            UseCaseResult.Failure("프로젝트 불러오기 실패", e)
        }
    }

    suspend fun loadProjects(firstDate: LocalDate, lastDate: LocalDate): UseCaseResult<Unit> {
        return try {
            _projects.value = useCases.getProjects(firstDate, lastDate).map { it.toPresentation() }
            UseCaseResult.Success(Unit)
        } catch (e: Exception) {
            UseCaseResult.Failure("프로젝트 필터링 실패", e)
        }
    }

    fun addProject(
        project: ProjectForPresentation,
        onSuccess: () -> Unit,
        onFailure: (message: String, throwable: Throwable?) -> Unit,
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {
            flow {
                try {
                    useCases.insertProject(project.toDomain())
                    getAlllProjects()
                    emit(UseCaseResult.Success(project))
                } catch (e: Exception) {
                    emit(UseCaseResult.Failure("프로젝트 추가 실패", e))
                }
            }.handleUseCaseResult(
                onSuccess = { onSuccess() },
                onFailure = { error -> onFailure(error.message ?: "", error.throwable) },
                onComplete = onComplete
            )
        }
    }

    fun deleteProject(
        project: ProjectForPresentation,
        onSuccess: () -> Unit,
        onFailure: (message: String, throwable: Throwable?) -> Unit,
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {
            flow {
                try {
                    useCases.deleteProject(project.toDomain().id)
                    getAlllProjects()
                    emit(UseCaseResult.Success(project))
                } catch (e: Exception) {
                    emit(UseCaseResult.Failure("프로젝트 삭제 실패", e))
                }
            }.handleUseCaseResult(
                onSuccess = { onSuccess() },
                onFailure = { error -> onFailure(error.message ?: "", error.throwable) },
                onComplete = onComplete
            )
        }
    }

    fun updateProject(
        project: ProjectForPresentation,
        onSuccess: () -> Unit,
        onFailure: (message: String, throwable: Throwable?) -> Unit,
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {
            flow {
                try {
                    useCases.updateProject(project.toDomain())
                    getAlllProjects()
                    emit(UseCaseResult.Success(project))
                } catch (e: Exception) {
                    emit(UseCaseResult.Failure("프로젝트 수정 실패", e))
                }
            }.handleUseCaseResult(
                onSuccess = { onSuccess() },
                onFailure = { error -> onFailure(error.message ?: "", error.throwable) },
                onComplete = onComplete
            )
        }

    }

    fun addComment(
        projectId: String,
        content: String,
        onSuccess: () -> Unit,
        onFailure: (message: String, throwable: Throwable?) -> Unit,
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {
            flow {
                try {
                    useCases.addComment(projectId, content)
                    emit(UseCaseResult.Success(Unit))
                } catch (e: Exception) {
                    emit(UseCaseResult.Failure("댓글 추가 실패", e))
                }
            }.handleUseCaseResult(
                onSuccess = { onSuccess() },
                onFailure = { error -> onFailure(error.message ?: "", error.throwable) },
                onComplete = onComplete
            )
        }
    }

    fun getComments(
        projectId: String,
        onSuccess: () -> Unit,
        onFailure: (message: String, throwable: Throwable?) -> Unit,
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {

            flow {
                try {
                    val comments = useCases.getComments(projectId).map { it.toPresentation() }
                    _comments.value = comments
                    emit(UseCaseResult.Success(Unit))

                } catch (e: Exception) {
                    emit(UseCaseResult.Failure("댓글 불러오기 실패", e))
                }

            }.handleUseCaseResult(
                onSuccess = { onSuccess() },
                onFailure = { error -> onFailure(error.message ?: "", error.throwable) },
                onComplete = onComplete
            )
        }

    }

    fun deleteComment(
        projectId: String,
        commentId: String,
        onSuccess: () -> Unit,
        onFailure: (message: String, throwable: Throwable?) -> Unit,
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {
            flow {
                try {
                    useCases.deleteComment(projectId, commentId)
                    emit(UseCaseResult.Success(Unit))
                } catch (e: Exception) {
                    emit(UseCaseResult.Failure("댓글 삭제 실패", e))
                }
            }.handleUseCaseResult(
                onSuccess = { onSuccess() },
                onFailure = { error -> onFailure(error.message ?: "", error.throwable) },
                onComplete = onComplete
            )
        }
    }

    fun updateComment(
        projectId: String,
        commentId: String,
        content: String,
        onSuccess: () -> Unit,
        onFailure: (message: String, throwable: Throwable?) -> Unit,
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {
            flow {
                try {
                    useCases.updateComment(projectId, commentId, content)
                    emit(UseCaseResult.Success(Unit))
                } catch (e: Exception) {
                    emit(UseCaseResult.Failure("댓글 수정 실패", e))
                }
            }.handleUseCaseResult(
                onSuccess = { onSuccess() },
                onFailure = { error -> onFailure(error.message ?: "", error.throwable) },
                onComplete = onComplete
            )

        }
    }


}
