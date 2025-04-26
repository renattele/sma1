package com.team6.smartbudget.features.categories.di

import com.team6.smartbudget.features.categories.data.di.CategoriesDataModule
import com.team6.smartbudget.features.categories.domain.di.CategoriesDomainModule
import com.team6.smartbudget.features.categories.presentation.di.CategoriesPresentationModule
import dagger.Module

@Module(
    includes = [
        CategoriesDataModule::class,
        CategoriesDomainModule::class,
        CategoriesPresentationModule::class,
    ],
)
interface CategoriesModule
