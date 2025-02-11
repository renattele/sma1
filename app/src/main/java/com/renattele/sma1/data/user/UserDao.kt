package com.renattele.sma1.data.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("select * from users where username = :username")
    suspend fun findByUsername(username: String): DbUserEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun add(user: DbUserEntity)
}