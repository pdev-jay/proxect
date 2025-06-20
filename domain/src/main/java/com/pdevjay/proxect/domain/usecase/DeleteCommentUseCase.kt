package com.pdevjay.proxect.domain.usecase

interface DeleteCommentUseCase {
    suspend operator fun invoke(projectId: String, commentId: String)
}