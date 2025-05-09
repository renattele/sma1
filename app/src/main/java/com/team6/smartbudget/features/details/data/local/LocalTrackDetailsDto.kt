package com.team6.smartbudget.features.details.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class LocalTrackDetailsDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("artist") val artist: String,
    @ColumnInfo("thumbnailUrl") val thumbnailUrl: String?,
    @ColumnInfo("published") val published: String,
    @ColumnInfo("summary") val summary: String,
    @ColumnInfo("updatedAt") val updatedAt: Long = System.currentTimeMillis(),
)