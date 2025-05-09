package com.team6.smartbudget.features.details.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalTrackDetailsDataSource {
    @Upsert
    suspend fun upsert(track: LocalTrackDetailsDto)

    @Query("select * from tracks where title = :title and artist = :artist")
    suspend fun getByTitleAndArtist(
        title: String,
        artist: String
    ): LocalTrackDetailsDto?

    @Delete
    suspend fun delete(track: LocalTrackDetailsDto)
}