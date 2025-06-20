package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.AddCommentUseCase
import javax.inject.Inject

class AddCommentUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
) : AddCommentUseCase {
    override suspend fun invoke(projectId: String, content: String) {
        repository.addComment(projectId, content)
    }
}
