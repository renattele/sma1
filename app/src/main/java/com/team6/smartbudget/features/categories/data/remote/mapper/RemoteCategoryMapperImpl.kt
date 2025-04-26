package com.team6.smartbudget.features.categories.data.remote.mapper

import android.content.Context
import com.team6.smartbudget.sma1.R
import com.team6.smartbudget.features.categories.data.mapper.RemoteCategoryMapper
import com.team6.smartbudget.features.categories.data.remote.RemoteCategoryAnalyticDto
import com.team6.smartbudget.features.categories.data.remote.RemoteCategoryDto
import com.team6.smartbudget.features.categories.domain.model.CategoryEntity
import javax.inject.Inject
import kotlin.uuid.Uuid

class RemoteCategoryMapperImpl @Inject constructor(
    private val context: Context,
) : RemoteCategoryMapper {
    private val categoryMap = mapOf(
        1L to CategoryAppearance(
            icon = R.drawable.ic_outline_fastfood_24,
            color = R.color.purple_200,
        ),
    )
    private val defaultCategoryAppearance = CategoryAppearance(
        icon = R.drawable.ic_outline_warning_24,
        color = R.color.teal_200,
    )

    private fun appearanceById(id: Long): CategoryAppearance =
        categoryMap[id] ?: defaultCategoryAppearance

    override fun toDomain(
        category: RemoteCategoryDto,
        categoryAnalytic: RemoteCategoryAnalyticDto,
    ): CategoryEntity = CategoryEntity(
        id = Uuid.fromLongs(category.id, 0),
        name = category.name,
        icon = appearanceById(category.id).icon.toString(),
        color = context.getColor(appearanceById(category.id).color),
        enabled = category.enabled,
        spentAmount = categoryAnalytic.spentAmount,
        spentPercent = categoryAnalytic.spentPercent,
        targetPercent = categoryAnalytic.targetPercent,
        targetAmount = categoryAnalytic.plannedAmount,
        transactionCount = categoryAnalytic.transactionCount,
        spentRelativePercent =
            if (categoryAnalytic.targetPercent != 0f) {
                categoryAnalytic.spentPercent / categoryAnalytic.targetPercent
            } else {
                0f
            },
    )

    override fun toRemote(category: CategoryEntity): RemoteCategoryDto = RemoteCategoryDto(
        id = category.id.toLongs { i1, i2 -> i1 },
        name = category.name,
        enabled = category.enabled,
        percent = category.targetPercent,
    )

    private data class CategoryAppearance(
        val icon: Int,
        val color: Int,
    )
}
