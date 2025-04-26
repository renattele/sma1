package com.team6.smartbudget.features.categories.domain.repository

import com.team6.smartbudget.core.util.ResultFlow
import com.team6.smartbudget.core.util.SilentResult
import com.team6.smartbudget.features.categories.domain.model.CategoryEntity
import kotlin.uuid.Uuid

interface CategoryRepository {
    fun getAll(): ResultFlow<List<CategoryEntity>, GetAllCategoriesError>

    fun get(id: Uuid): ResultFlow<CategoryEntity, GetCategoryError>

    suspend fun create(category: CategoryEntity): SilentResult<CreateCategoryError>

    sealed class GetAllCategoriesError {
        data object NoNetwork : GetAllCategoriesError()
    }

    sealed class GetCategoryError {
        data object NoNetwork : GetCategoryError()

        data object NotFound : GetCategoryError()
    }

    sealed class CreateCategoryError {
        data object NoNetwork : CreateCategoryError()

        data object AlreadyExists : CreateCategoryError()
    }
}
