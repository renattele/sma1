package com.team6.smartbudget.features.categories.domain.di

import com.team6.smartbudget.features.categories.domain.usecase.GetEnabledCategoriesUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface CategoriesDomainModule {
    @Binds
    @Singleton
    fun bindGetEnabledCategoriesUseCase(
        useCase: GetEnabledCategoriesUseCase,
    ): GetEnabledCategoriesUseCase
}
