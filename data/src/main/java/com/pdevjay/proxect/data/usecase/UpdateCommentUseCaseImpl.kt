package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.UpdateCommentUseCase
import javax.inject.Inject

class UpdateCommentUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
) : UpdateCommentUseCase {
    override suspend fun invoke(projectId: String, commentId: String, content: String) {
        repository.updateComment(projectId, commentId, content)
    }
}