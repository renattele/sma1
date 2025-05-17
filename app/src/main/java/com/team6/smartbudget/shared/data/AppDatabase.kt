package com.team6.smartbudget.shared.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.team6.smartbudget.features.details.data.local.LocalTrackDetailsDataSource
import com.team6.smartbudget.features.details.data.local.LocalTrackDetailsDto

@Database(entities = [
    LocalTrackDetailsDto::class
], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun trackDetailsDao(): LocalTrackDetailsDataSource
}