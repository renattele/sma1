package com.renattele.sma1.data.wallpaper

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.UUID

@Dao
interface WallpaperDao {
    @Query("select * from wallpapers where author = :id")
    suspend fun getAllFromAuthor(id: UUID): List<DbWallpaperEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(entity: DbWallpaperEntity)

    @Query("delete from wallpapers where id = :id")
    suspend fun deleteById(id: UUID): Int
}