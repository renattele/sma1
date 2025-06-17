package com.team6.smartbudget.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
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
            val dataStore = serializableDataStore(
                context,
                name,
                defaultValue,
                serializer,
                json,
            )
            return DataStoreLocalStore(dataStore)
        }
    }
}

private fun <T> serializableDataStore(
    context: Context,
    fileName: String,
    defaultValue: T,
    serializer: KSerializer<T>,
    json: Json,
): DataStore<T> {
    val dataStoreSerializer =
        KotlinSerializationDataStoreSerializer(
            defaultValue = defaultValue,
            serializer = serializer,
            json = json,
        )
    val store =
        dataStore(
            fileName = fileName,
            serializer = dataStoreSerializer,
        )
    return store.getValue(context, dataStoreSerializer::defaultValue)
}