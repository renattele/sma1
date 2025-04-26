package com.team6.smartbudget.features.categories.data.di

import com.team6.smartbudget.features.categories.data.local.mapper.LocalCategoryMapperImpl
import com.team6.smartbudget.features.categories.data.mapper.LocalCategoryMapper
import com.team6.smartbudget.features.categories.data.mapper.RemoteCategoryMapper
import com.team6.smartbudget.features.categories.data.remote.RemoteCategoryAnalyticDataSource
import com.team6.smartbudget.features.categories.data.remote.RemoteCategoryDataSource
import com.team6.smartbudget.features.categories.data.remote.mapper.RemoteCategoryMapperImpl
import com.team6.smartbudget.features.categories.data.repository.CategoryRepositoryImpl
import com.team6.smartbudget.features.categories.domain.repository.CategoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [CategoriesProvidesModule::class])
interface CategoriesDataModule {
    @Binds
    @Singleton
    fun bindRemoteCategoriesMapper(mapper: RemoteCategoryMapperImpl): RemoteCategoryMapper

    @Binds
    @Singleton
    fun bindLocalCategoriesMapper(mapper: LocalCategoryMapperImpl): LocalCategoryMapper

    @Binds
    @Singleton
    fun bindCategoryRepository(repository: CategoryRepositoryImpl): CategoryRepository
}

@Module
open class CategoriesProvidesModule {
    @Provides
    @Singleton
    fun provideRemoteCategoriesDataSource(retrofit: Retrofit): RemoteCategoryDataSource =
        retrofit.create(RemoteCategoryDataSource::class.java)

    @Provides
    @Singleton
    fun provideRemoteCategoriesAnalyticDataSource(
        retrofit: Retrofit,
    ): RemoteCategoryAnalyticDataSource =
        retrofit.create(RemoteCategoryAnalyticDataSource::class.java)
}
