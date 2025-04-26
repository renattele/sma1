package com.team6.smartbudget.features.categories.domain.model

import kotlin.uuid.Uuid

data class CategoryEntity(
    val id: Uuid,
    val name: String,
    val icon: String,
    val color: Int,
    val enabled: Boolean,
    val spentAmount: Float,
    val spentPercent: Float,
    val targetPercent: Float,
    val targetAmount: Float,
    val transactionCount: Int,
    val spentRelativePercent: Float,
)
