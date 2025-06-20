package com.pdevjay.proxect.domain.usecase

interface AddCommentUseCase {
    suspend operator fun invoke(projectId: String, content: String)
}