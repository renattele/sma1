package com.team6.smartbudget.features.categories.presentation.mapper

import com.team6.smartbudget.features.categories.domain.model.CategoryEntity
import com.team6.smartbudget.features.categories.presentation.UiCategoryEntity

interface UiCategoryEntityMapper {
    fun toDomain(category: UiCategoryEntity): CategoryEntity

    fun toUi(category: CategoryEntity): UiCategoryEntity
}
