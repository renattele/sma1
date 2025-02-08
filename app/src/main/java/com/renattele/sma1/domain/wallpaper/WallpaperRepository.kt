package com.renattele.sma1.domain.wallpaper

import java.util.UUID

interface WallpaperRepository {
    suspend fun getWallpapers(): List<WallpaperEntity>
    suspend fun addWallpaper(request: AddWallpaperRequest): AddWallpaperResponse
    suspend fun deleteWallpaperById(id: UUID): DeleteWallpaperResponse
}

data class AddWallpaperRequest(
    val name: String,
    val description: String,
    val pictureUri: String
)

sealed class AddWallpaperResponse {
    data object Success: AddWallpaperResponse()
    data object Failure: AddWallpaperResponse()
}

sealed class DeleteWallpaperResponse {
    data object Success: DeleteWallpaperResponse()
    data object IdNotFound: DeleteWallpaperResponse()
}