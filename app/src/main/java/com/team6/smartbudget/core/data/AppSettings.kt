package com.team6.smartbudget.core.data

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val enabled: Boolean = false,
)
