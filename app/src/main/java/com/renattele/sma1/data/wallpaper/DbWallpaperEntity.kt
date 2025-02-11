package com.renattele.sma1.data.wallpaper

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.renattele.sma1.data.user.DbUserEntity
import com.renattele.sma1.domain.wallpaper.WallpaperEntity
import java.util.UUID

@Entity(
    "wallpapers",
    foreignKeys = [
        ForeignKey(
            entity = DbUserEntity::class,
            parentColumns = ["id"],
            childColumns = ["author"]
        )
    ]
)
data class DbWallpaperEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: UUID = UUID.randomUUID(),
    @ColumnInfo("author") val author: UUID,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("description") val description: String?,
    @ColumnInfo("picture_uri") val pictureUri: String,
)

fun DbWallpaperEntity.toEntity(): WallpaperEntity =
    WallpaperEntity(
        id = id,
        author = author,
        name = name,
        description = description,
        pictureUri = pictureUri
    )