package com.team6.smartbudget.features.categories.data.local.mapper

import com.team6.smartbudget.features.categories.data.local.LocalCategoryDto
import com.team6.smartbudget.features.categories.data.mapper.LocalCategoryMapper
import com.team6.smartbudget.features.categories.domain.model.CategoryEntity
import javax.inject.Inject

class LocalCategoryMapperImpl @Inject constructor() : LocalCategoryMapper {
    override fun toDomain(local: LocalCategoryDto): CategoryEntity = CategoryEntity(
        id = local.id,
        name = local.name,
        icon = local.icon,
        color = local.color,
        enabled = local.enabled,
        spentAmount = local.spentAmount,
        spentPercent = local.spentPercent,
        targetPercent = local.targetPercent,
        targetAmount = local.targetAmount,
        transactionCount = local.transactionCount,
        spentRelativePercent = local.spentRelativePercent,
    )

    override fun toLocal(domain: CategoryEntity): LocalCategoryDto = LocalCategoryDto(
        id = domain.id,
        name = domain.name,
        icon = domain.icon,
        color = domain.color,
        enabled = domain.enabled,
        spentAmount = domain.spentAmount,
        spentPercent = domain.spentPercent,
        targetPercent = domain.targetPercent,
        targetAmount = domain.targetAmount,
        transactionCount = domain.transactionCount,
        spentRelativePercent = domain.spentRelativePercent,
    )
}
