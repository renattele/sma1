package com.team6.smartbudget.shared.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.team6.smartbudget.sma1.BuildConfig
import com.team6.smartbudget.core.data.UuidTypeConverter
import com.team6.smartbudget.features.categories.data.local.LocalCategoryDataSource
import com.team6.smartbudget.features.categories.data.local.LocalCategoryDto

@Database(
    entities = [LocalCategoryDto::class],
    version = BuildConfig.DATABASE_VERSION,
)
@TypeConverters(UuidTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDataSource(): LocalCategoryDataSource
}
