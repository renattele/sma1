package com.renattele.sma1.data.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.renattele.sma1.domain.user.UserEntity
import java.util.UUID

@Entity("users", indices = [
    Index(value = ["username"], unique = true)
])
data class DbUserEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo("username") val username: String,
    @ColumnInfo("password_hash") val passwordHash: String
)

fun DbUserEntity.toEntity(): UserEntity =
    UserEntity(
        id = id,
        username = username,
        passwordHash = passwordHash
    )