package com.team6.smartbudget.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import com.team6.smartbudget.core.domain.LocalStore
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DataStoreLocalStore<T> @Inject constructor(
    private val dataStore: DataStore<T>,
) : LocalStore<T> {
    override val data: Flow<T>
        get() = dataStore.data

    override suspend fun update(update: T.() -> T): T = dataStore.updateData(update)

    class Factory @Inject constructor(
        private val context: Context,
        private val json: Json,
    ) : LocalStore.Factory {
        override fun <T> create(
            serializer: KSerializer<T>,
            name: String,
            defaultValue: T,
        ): LocalStore<T> {
            val dataStore = context.serializableDataStore(
                name,
                defaultValue,
                serializer = serializer,
                json,
            )
            return DataStoreLocalStore(dataStore)
        }
    }
}