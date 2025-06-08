package com.team6.smartbudget.core.data.repository

import com.team6.smartbudget.core.data.local.LocalApiKeySettings
import com.team6.smartbudget.core.data.remote.RemoteApiKeyCheckDataSource
import com.team6.smartbudget.core.domain.LocalStore
import com.team6.smartbudget.core.domain.exception.InvalidApiKeyException
import com.team6.smartbudget.core.domain.repository.ApiKeyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ApiKeyRepositoryImpl @Inject constructor(
    private val apiKeyStore: LocalStore<LocalApiKeySettings>,
    private val remoteApiKeyCheckDataSource: RemoteApiKeyCheckDataSource
) : ApiKeyRepository {
    override fun apiKey(): Flow<String?> {
        return apiKeyStore.data.map { it.apiKey }
    }

    override suspend fun updateApiKey(apiKey: String): Result<Unit> = runCatching {
        apiKeyStore.update {
            copy(apiKey = apiKey)
        }
        val response = remoteApiKeyCheckDataSource.getToken(apiKey)
        if (!response.isSuccessful) {
            throw InvalidApiKeyException()
        }
    }
}