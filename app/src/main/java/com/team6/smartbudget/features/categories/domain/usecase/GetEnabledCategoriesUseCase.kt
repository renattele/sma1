package com.team6.smartbudget.features.categories.domain.usecase

import com.team6.smartbudget.core.util.ResultFlow
import com.team6.smartbudget.core.util.mapError
import com.team6.smartbudget.core.util.mapSuccess
import com.team6.smartbudget.features.categories.domain.model.CategoryEntity
import com.team6.smartbudget.features.categories.domain.repository.CategoryRepository
import com.team6.smartbudget.features.categories.domain.usecase.GetEnabledCategoriesUseCase.Error
import javax.inject.Inject

class GetEnabledCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : () -> ResultFlow<List<CategoryEntity>, Error> {
    override fun invoke(): ResultFlow<List<CategoryEntity>, Error> = categoryRepository
        .getAll()
        .mapSuccess { categories ->
            categories.filter { it.enabled }
        }.mapError { error ->
            when (error) {
                CategoryRepository.GetAllCategoriesError.NoNetwork -> Error.NoInternet
            }
        }

    sealed interface Error {
        object NoInternet : Error
    }
}
