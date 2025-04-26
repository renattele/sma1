package com.team6.smartbudget.shared.di

import android.content.Context
import androidx.room.Room
import com.team6.smartbudget.sma1.R
import com.team6.smartbudget.shared.data.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
open class SharedModule {
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
}
