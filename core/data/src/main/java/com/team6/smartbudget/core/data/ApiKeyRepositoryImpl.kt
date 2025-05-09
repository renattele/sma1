package com.team6.smartbudget.core.data

import androidx.datastore.core.DataStore
import com.team6.smartbudget.core.data.local.LocalApiKeySettings
import com.team6.smartbudget.core.domain.ApiKeyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ApiKeyRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<LocalApiKeySettings>,
) : ApiKeyRepository {
    override fun apiKey(): Flow<String?> {
        return dataStore.data.map { it.apiKey }
    }

    override suspend fun updateApiKey(apiKey: String?) {
        dataStore.updateData { settings ->
            settings.copy(apiKey = apiKey)
        }
    }
}