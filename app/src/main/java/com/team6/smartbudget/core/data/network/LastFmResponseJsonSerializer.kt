package com.team6.smartbudget.core.data.network

import com.team6.smartbudget.core.domain.network.LastFmResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.jsonObject

class LastFmResponseJsonSerializer<T>(
    private val dataSerializer: KSerializer<T>,
) : KSerializer<LastFmResponse<T>> {
    private val errorSerializer = LastFmResponse.Error.serializer()

    override val descriptor: SerialDescriptor
        get() = dataSerializer.descriptor

    override fun serialize(
        encoder: Encoder,
        value: LastFmResponse<T>,
    ) {
        require(encoder is JsonEncoder) {
            "LastFmResponse can only be serialized by Json"
        }

        when (value) {
            is LastFmResponse.Success ->
                encoder.encodeSerializableValue(dataSerializer, value.data)

            is LastFmResponse.Error ->
                encoder.encodeSerializableValue(errorSerializer, value)
        }
    }

    override fun deserialize(decoder: Decoder): LastFmResponse<T> {
        require(decoder is JsonDecoder) { "LastFmResponse can only be deserialized by Json" }

        val element = decoder.decodeJsonElement().jsonObject
        return if ("error" in element) {
            decoder.json.decodeFromJsonElement(errorSerializer, element)
        } else {
            val data = decoder.json.decodeFromJsonElement(dataSerializer, element)
            LastFmResponse.Success(data)
        }
    }
}
