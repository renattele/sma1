package com.team6.smartbudget.core.domain.network

import kotlinx.serialization.Serializable

sealed class LastFmResponse<out T> {
    @Serializable
    data class Success<out T>(val data: T) : LastFmResponse<T>()

    @Serializable
    data class Error(val message: String, val error: Int) : LastFmResponse<Nothing>()
}
