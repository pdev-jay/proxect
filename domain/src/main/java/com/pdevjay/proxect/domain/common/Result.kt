package com.pdevjay.proxect.domain.common

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>

    sealed interface Failure : Result<Nothing> {
        val message: String
        val throwable: Throwable?

        data class KnownError(
            override val message: String,
            override val throwable: Throwable? = null
        ) : Failure

        data class UnknownError(
            override val message: String = "알 수 없는 오류가 발생했습니다.",
            override val throwable: Throwable? = null
        ) : Failure
    }
}
