package com.team6.smartbudget.shared.di

import com.team6.smartbudget.features.categories.data.local.LocalCategoryDataSource
import com.team6.smartbudget.shared.data.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class DatabaseModule {
    @Provides
    @Singleton
    fun provideLocalCategoriesDataSource(appDatabase: AppDatabase): LocalCategoryDataSource =
        appDatabase.categoryDataSource()
}
