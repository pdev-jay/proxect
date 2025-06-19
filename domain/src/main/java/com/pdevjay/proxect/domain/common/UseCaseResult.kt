package com.pdevjay.proxect.domain.common

import kotlinx.coroutines.flow.Flow

sealed interface UseCaseResult<out T> {
    data class Success<T>(val data: T) : UseCaseResult<T>

    data class Failure(
        val message: String? = "",
        val throwable: Throwable? = null
    ) : UseCaseResult<Nothing>
}

suspend inline fun <T> Flow<UseCaseResult<T>>.handleUseCaseResult(
    crossinline onSuccess: (T) -> Unit = {},
    crossinline onFailure: (UseCaseResult.Failure) -> Unit = {},
    crossinline onComplete: () -> Unit = {}
) {
    collect { result ->
        when (result) {
            is UseCaseResult.Success -> onSuccess(result.data)
            is UseCaseResult.Failure -> onFailure(result)
        }
        onComplete()
    }
}
