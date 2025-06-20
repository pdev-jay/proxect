package com.pdevjay.proxect.domain.usecase

interface UpdateCommentUseCase {
    suspend operator fun invoke(projectId: String, commentId: String, content: String)

}