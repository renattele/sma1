package com.team6.smartbudget.features.categories.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.Uuid

@Entity(tableName = "categories")
data class LocalCategoryDto(
    @PrimaryKey val id: Uuid,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("icon") val icon: String,
    @ColumnInfo("color") val color: Int,
    @ColumnInfo("enabled") val enabled: Boolean,
    @ColumnInfo("spentAmount") val spentAmount: Float,
    @ColumnInfo("spentPercent") val spentPercent: Float,
    @ColumnInfo("targetPercent") val targetPercent: Float,
    @ColumnInfo("targetAmount") val targetAmount: Float,
    @ColumnInfo("transactionCount") val transactionCount: Int,
    @ColumnInfo("spentRelativePercent") val spentRelativePercent: Float,
)
