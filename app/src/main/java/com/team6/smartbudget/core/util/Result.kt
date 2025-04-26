// Needed to suppress because it's utility class
@file:Suppress("TooManyFunctions")

package com.team6.smartbudget.core.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed class Result<out T, out E> {
    data class Success<out T>(
        val value: T,
    ) : Result<T, Nothing>()

    data class Failure<out E>(
        val error: E,
    ) : Result<Nothing, E>()
}

typealias ResultFlow<T, E> = Flow<Result<T, E>>

typealias SilentResult<E> = Result<Unit, E>

fun <T> success(data: T): Result.Success<T> = Result.Success(data)

fun success(): SilentResult<Nothing> = Result.Success(Unit)

fun <E> failure(error: E): Result.Failure<E> = Result.Failure(error)

inline fun <T, E, R> Result<T, E>.map(mapper: (T) -> R): Result<R, E> = when (this) {
    is Result.Success -> Result.Success(mapper(value))
    is Result.Failure -> this
}

inline fun <T, E, R> Result<T, E>.mapResult(mapper: (T) -> Result<R, E>): Result<R, E> =
    when (this) {
        is Result.Success -> mapper(value)
        is Result.Failure -> this
    }

inline fun <T, E, R> Result<T, E>.mapError(mapper: (E) -> R): Result<T, R> = when (this) {
    is Result.Success -> this
    is Result.Failure -> Result.Failure(mapper(error))
}

fun <T> Result<T, *>.getOrNull(): T? = when (this) {
    is Result.Success -> value
    is Result.Failure -> null
}

fun <T> Result<T, *>.getOrDefault(default: T): T = when (this) {
    is Result.Success -> value
    is Result.Failure -> default
}

@Suppress("UNCHECKED_CAST")
inline fun <T, R : Result<T, *>> R.onSuccess(action: (value: T) -> Unit): R {
    if (this is Result.Success<*>) {
        action(value as T)
    }
    return this
}

@Suppress("UNCHECKED_CAST")
inline fun <T, R : Result<*, T>> R.onFailure(action: (error: T) -> Unit): R {
    if (this is Result.Failure<*>) {
        action(error as T)
    }
    return this
}

inline fun <T> runResulting(block: () -> T): Result<T, Throwable> {
    val result = runCatching {
        block()
    }
    return when (result.isSuccess) {
        true -> Result.Success(result.getOrThrow())
        false -> Result.Failure(result.exceptionOrNull() ?: Throwable("Unknown error"))
    }
}

inline fun <T, E, R> Flow<Result<T, E>>.mapSuccess(
    crossinline mapper: (T) -> R,
): Flow<Result<R, E>> = map { result ->
    result.map(mapper)
}

inline fun <T, E, R> Flow<Result<T, E>>.mapError(
    crossinline mapper: (E) -> R,
): Flow<Result<T, R>> = map { result ->
    result.mapError(mapper)
}

fun <E> Result<*, E>.errorOrNull(): E? = when (this) {
    is Result.Failure -> error
    else -> null
}
