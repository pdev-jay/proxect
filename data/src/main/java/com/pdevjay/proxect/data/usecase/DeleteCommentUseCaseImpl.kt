package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.DeleteCommentUseCase
import javax.inject.Inject

class DeleteCommentUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository

) : DeleteCommentUseCase {
    override suspend fun invoke(projectId: String, commentId: String) {
        repository.deleteComment(projectId, commentId)
    }
}