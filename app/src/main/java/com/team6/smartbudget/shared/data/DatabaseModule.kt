package com.team6.smartbudget.shared.data

import android.content.Context
import androidx.room.Room
import com.team6.smartbudget.core.ui.R
import com.team6.smartbudget.features.details.data.local.LocalTrackDetailsDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class DatabaseModule {
    @Provides
    @Singleton
    open fun appDatabase(
        context: Context,
    ): AppDatabase {
        val dbName = context.getString(R.string.database_name)
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                dbName,
            ).build()
    }

    @Provides
    @Singleton
    open fun provideTrackDao(
        appDatabase: AppDatabase,
    ): LocalTrackDetailsDataSource = appDatabase.trackDetailsDao()
}