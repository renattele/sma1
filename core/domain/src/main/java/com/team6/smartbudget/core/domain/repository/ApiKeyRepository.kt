package com.team6.smartbudget.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface ApiKeyRepository {
    fun apiKey(): Flow<String?>
    suspend fun updateApiKey(apiKey: String): Result<Unit>
}