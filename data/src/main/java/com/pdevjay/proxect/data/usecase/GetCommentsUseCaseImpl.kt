package com.pdevjay.proxect.data.usecase

import com.pdevjay.proxect.domain.model.Comment
import com.pdevjay.proxect.domain.repository.ProjectRepository
import com.pdevjay.proxect.domain.usecase.GetCommentsUseCase
import javax.inject.Inject

class GetCommentsUseCaseImpl @Inject constructor(
    private val repository: ProjectRepository
) : GetCommentsUseCase {
    override suspend fun invoke(projectId: String): List<Comment> {
        return repository.getComments(projectId)
    }
}