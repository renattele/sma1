package com.renattele.sma1.domain.wallpaper

import java.util.UUID

data class WallpaperEntity(
    val id: UUID = UUID.randomUUID(),
    val author: UUID,
    val name: String,
    val description: String?,
    val pictureUri: String
)