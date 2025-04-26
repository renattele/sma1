package com.team6.smartbudget.features.categories.presentation.mapper

import com.team6.smartbudget.features.categories.domain.model.CategoryEntity
import com.team6.smartbudget.features.categories.presentation.UiCategoryEntity
import javax.inject.Inject

class UiCategoryEntityMapperImpl @Inject constructor() : UiCategoryEntityMapper {
    override fun toDomain(category: UiCategoryEntity): CategoryEntity = CategoryEntity(
        id = category.id,
        name = category.name,
        icon = category.icon,
        color = category.color,
        targetPercent = category.targetPercent,
        enabled = category.enabled,
        spentAmount = category.spentAmount,
        spentPercent = category.spentPercent,
        targetAmount = category.targetAmount,
        transactionCount = category.transactionCount,
        spentRelativePercent = category.spentRelativePercent,
    )

    override fun toUi(category: CategoryEntity): UiCategoryEntity = UiCategoryEntity(
        id = category.id,
        name = category.name,
        icon = category.icon,
        color = category.color,
        targetPercent = category.targetPercent,
        enabled = category.enabled,
        spentAmount = category.spentAmount,
        spentPercent = category.spentPercent,
        targetAmount = category.targetAmount,
        transactionCount = category.transactionCount,
        spentRelativePercent = category.spentRelativePercent,
    )
}
