package com.team6.smartbudget.features.categories.data.mapper

import com.team6.smartbudget.features.categories.data.remote.RemoteCategoryAnalyticDto
import com.team6.smartbudget.features.categories.data.remote.RemoteCategoryDto
import com.team6.smartbudget.features.categories.domain.model.CategoryEntity

interface RemoteCategoryMapper {
    fun toDomain(
        category: RemoteCategoryDto,
        categoryAnalytic: RemoteCategoryAnalyticDto,
    ): CategoryEntity

    fun toRemote(category: CategoryEntity): RemoteCategoryDto
}
