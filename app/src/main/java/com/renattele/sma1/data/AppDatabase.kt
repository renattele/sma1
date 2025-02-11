package com.renattele.sma1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.renattele.sma1.data.user.DbUserEntity
import com.renattele.sma1.data.user.UserDao
import com.renattele.sma1.data.wallpaper.DbWallpaperEntity
import com.renattele.sma1.data.wallpaper.WallpaperDao

@Database(entities = [DbUserEntity::class, DbWallpaperEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val wallpaperDao: WallpaperDao

    companion object {
        private const val DB_NAME = "data.db"
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .build()
        }
    }
}