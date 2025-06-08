package com.team6.smartbudget.core.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteApiKeyCheckResponse(
    @SerialName("token") val token: String,
)