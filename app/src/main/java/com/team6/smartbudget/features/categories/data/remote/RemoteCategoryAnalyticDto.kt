package com.team6.smartbudget.features.categories.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCategoryAnalyticDto(
    @SerialName("categoryId") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("spentAmount") val spentAmount: Float,
    @SerialName("spentPercent") val spentPercent: Float,
    @SerialName("targetPercent") val targetPercent: Float,
    @SerialName("plannedAmount") val plannedAmount: Float,
    @SerialName("transactionCount") val transactionCount: Int,
    @SerialName("enabled") val enabled: Boolean = true,
)
