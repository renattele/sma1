package com.team6.smartbudget.features.categories.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

@Dao
interface LocalCategoryDataSource {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<LocalCategoryDto>>

    @Query("SELECT * FROM categories")
    fun getAll(): List<LocalCategoryDto>

    @Upsert
    suspend fun upsertCategory(category: LocalCategoryDto)

    @Query("DELETE FROM categories WHERE id = :categoryId")
    suspend fun deleteCategoryById(categoryId: Uuid)

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun getCategoryById(categoryId: Uuid): Flow<LocalCategoryDto?>
}
