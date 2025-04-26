package com.team6.smartbudget.features.categories.presentation.di

import com.team6.smartbudget.features.categories.presentation.mapper.UiCategoryEntityMapper
import com.team6.smartbudget.features.categories.presentation.mapper.UiCategoryEntityMapperImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface CategoriesPresentationModule {
    @Binds
    @Singleton
    fun bindUiCategoryEntityMapper(mapper: UiCategoryEntityMapperImpl): UiCategoryEntityMapper
}
