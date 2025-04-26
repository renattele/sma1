package com.team6.smartbudget.features.categories.presentation

import kotlin.uuid.Uuid

data class UiCategoryEntity(
    val id: Uuid,
    val name: String,
    val icon: String,
    val color: Int,
    val enabled: Boolean,
    val transactionCount: Int,
    val targetAmount: Float,
    val targetPercent: Float,
    val spentAmount: Float,
    val spentPercent: Float,
    val spentRelativePercent: Float,
)
