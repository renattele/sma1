package com.team6.smartbudget.core.data

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.io.InputStream
import java.io.OutputStream

class KotlinSerializationDataStoreSerializer<T>(
    override val defaultValue: T,
    private val serializer: KSerializer<T>,
    private val json: Json = Json,
) : Serializer<T> {
    override suspend fun readFrom(input: InputStream): T {
        try {
            val rawBytes = input.readBytes()
            return json.decodeFromString(
                serializer,
                rawBytes.decodeToString(),
            )
        } catch (ex: SerializationException) {
            throw CorruptionException("Unable to read UserPrefs", ex)
        }
    }

    override suspend fun writeTo(
        value: T,
        output: OutputStream,
    ) {
        val encodedValue = json.encodeToString(serializer, value)
        val bytes = encodedValue.encodeToByteArray()
        output.write(bytes)
    }
}

inline fun <reified T> Context.serializableDataStore(
    fileName: String,
    defaultValue: T,
    json: Json = Json,
): DataStore<T> {
    val serializer =
        KotlinSerializationDataStoreSerializer(
            defaultValue = defaultValue,
            serializer = serializer(),
            json = json,
        )
    val store =
        dataStore(
            fileName = fileName,
            serializer = serializer,
        )
    return store.getValue(this, serializer::defaultValue)
}
