package com.team6.smartbudget.core.data.local

import kotlinx.serialization.Serializable

@Serializable
data class LocalApiKeySettings(
    val apiKey: String? = null,
)