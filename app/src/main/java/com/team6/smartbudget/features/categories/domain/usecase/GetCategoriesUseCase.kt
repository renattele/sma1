package com.team6.smartbudget.features.categories.domain.usecase

import com.team6.smartbudget.core.util.ResultFlow
import com.team6.smartbudget.core.util.mapError
import com.team6.smartbudget.features.categories.domain.model.CategoryEntity
import com.team6.smartbudget.features.categories.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : () -> ResultFlow<List<CategoryEntity>, GetCategoriesUseCase.Error> {
    override fun invoke(): ResultFlow<List<CategoryEntity>, Error> =
        categoryRepository.getAll().mapError { error ->
            when (error) {
                CategoryRepository.GetAllCategoriesError.NoNetwork -> Error.NoInternet
            }
        }

    sealed interface Error {
        data object NoInternet : Error
    }
}
