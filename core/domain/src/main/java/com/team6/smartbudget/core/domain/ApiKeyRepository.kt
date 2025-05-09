package com.team6.smartbudget.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ApiKeyRepository {
    fun apiKey(): Flow<String?>
    suspend fun updateApiKey(apiKey: String?)
}