package com.team6.smartbudget.features.categories.data.mapper

import com.team6.smartbudget.features.categories.data.local.LocalCategoryDto
import com.team6.smartbudget.features.categories.domain.model.CategoryEntity

interface LocalCategoryMapper {
    fun toDomain(local: LocalCategoryDto): CategoryEntity

    fun toLocal(domain: CategoryEntity): LocalCategoryDto
}
