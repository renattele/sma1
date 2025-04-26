package com.team6.smartbudget.core.domain.network

import com.team6.smartbudget.core.util.Result

typealias NetworkResult<T> = Result<T, NetworkError>
typealias SilentNetworkResult = NetworkResult<Unit>

sealed interface NetworkError {
    data object NoInternet : NetworkError

    data class Unknown(
        val cause: Throwable,
    ) : NetworkError

    data class HttpError(
        val code: Int,
        val message: String,
    ) : NetworkError
}
