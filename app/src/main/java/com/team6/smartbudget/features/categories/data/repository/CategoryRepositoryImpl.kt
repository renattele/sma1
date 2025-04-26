package com.team6.smartbudget.features.categories.data.repository

import com.team6.smartbudget.core.domain.network.SilentNetworkResult
import com.team6.smartbudget.core.util.ResultFlow
import com.team6.smartbudget.core.util.SilentResult
import com.team6.smartbudget.core.util.failure
import com.team6.smartbudget.core.util.mapError
import com.team6.smartbudget.core.util.mapResult
import com.team6.smartbudget.core.util.success
import com.team6.smartbudget.core.util.toSharedFlow
import com.team6.smartbudget.features.categories.data.local.LocalCategoryDataSource
import com.team6.smartbudget.features.categories.data.mapper.LocalCategoryMapper
import com.team6.smartbudget.features.categories.data.mapper.RemoteCategoryMapper
import com.team6.smartbudget.features.categories.data.remote.RemoteCategoryAnalyticDataSource
import com.team6.smartbudget.features.categories.data.remote.RemoteCategoryDataSource
import com.team6.smartbudget.features.categories.domain.model.CategoryEntity
import com.team6.smartbudget.features.categories.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.uuid.Uuid

class CategoryRepositoryImpl @Inject constructor(
    private val localDataSource: LocalCategoryDataSource,
    private val remoteDataSource: RemoteCategoryDataSource,
    private val remoteAnalyticDataSource: RemoteCategoryAnalyticDataSource,
    private val coroutineScope: CoroutineScope,
    private val remoteCategoryMapper: RemoteCategoryMapper,
    private val localCategoryMapper: LocalCategoryMapper,
) : CategoryRepository {
    private val allCategoriesFlow = flow {
        refreshCategories()
        val localData = localDataSource.getAllCategories().map { categories ->
            categories.map(localCategoryMapper::toDomain)
        }
        emitAll(localData.map(::success))
    }.toSharedFlow(coroutineScope)

    private fun refreshCategories(): Deferred<SilentNetworkResult?> {
        return coroutineScope.async(Dispatchers.IO) {
            val remoteData = remoteDataSource.getAll()
            val remoteCategories = remoteData.body() ?: emptyList()
            val remoteAnalyticData = remoteAnalyticDataSource.getAll()
            val categoryAnalytics = remoteAnalyticData.body() ?: emptyList()
            val categories =
                remoteCategories.mapNotNull { category ->
                    val categoryAnalytics = categoryAnalytics.find { it.id == category.id }
                    if (categoryAnalytics == null) return@mapNotNull null
                    remoteCategoryMapper.toDomain(category, categoryAnalytics)
                }
            categories.forEach { category ->
                localDataSource.upsertCategory(localCategoryMapper.toLocal(category))
            }
            return@async null
        }
    }

    override fun getAll(): ResultFlow<
        List<CategoryEntity>,
        CategoryRepository.GetAllCategoriesError,
        > =
        allCategoriesFlow

    override fun get(id: Uuid): ResultFlow<CategoryEntity, CategoryRepository.GetCategoryError> =
        getAll().map { result ->
            result
                .mapError { error ->
                    when (error) {
                        CategoryRepository.GetAllCategoriesError.NoNetwork ->
                            CategoryRepository.GetCategoryError.NoNetwork
                    }
                }.mapResult { categories ->
                    categories
                        .find { it.id == id }
                        ?.let(::success)
                        ?: failure(CategoryRepository.GetCategoryError.NotFound)
                }
        }

    override suspend fun create(
        category: CategoryEntity,
    ): SilentResult<CategoryRepository.CreateCategoryError> {
        val response = remoteDataSource.insert(remoteCategoryMapper.toRemote(category))
        if (!response.isSuccessful) {
            return failure(CategoryRepository.CreateCategoryError.NoNetwork)
        }
        return success()
    }
}
