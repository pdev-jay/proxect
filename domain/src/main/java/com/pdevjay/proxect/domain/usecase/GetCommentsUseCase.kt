package com.pdevjay.proxect.domain.usecase

import com.pdevjay.proxect.domain.model.Comment

interface GetCommentsUseCase {
    suspend operator fun invoke(projectId: String): List<Comment>
}