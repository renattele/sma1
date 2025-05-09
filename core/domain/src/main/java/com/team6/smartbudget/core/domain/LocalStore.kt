package com.team6.smartbudget.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer

interface LocalStore<T> {
    val data: Flow<T>

    suspend fun update(update: T.() -> T): T

    interface Factory {
        fun <T> create(
            serializer: KSerializer<T>,
            name: String,
            defaultValue: T,
        ): LocalStore<T>
    }
}