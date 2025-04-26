package com.team6.smartbudget.features.categories.data.remote

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCategoryDto(
    @SerialName("categoryId") @Contextual val id: Long,
    @SerialName("name") val name: String,
    @SerialName("targetPercent") val percent: Float,
    @SerialName("enabled") val enabled: Boolean,
)
