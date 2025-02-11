package com.renattele.sma1.data.wallpaper

import com.renattele.sma1.domain.user.UserRepository
import com.renattele.sma1.domain.wallpaper.AddWallpaperRequest
import com.renattele.sma1.domain.wallpaper.AddWallpaperResponse
import com.renattele.sma1.domain.wallpaper.DeleteWallpaperResponse
import com.renattele.sma1.domain.wallpaper.WallpaperEntity
import com.renattele.sma1.domain.wallpaper.WallpaperRepository
import java.util.UUID

class WallpaperRepositoryImpl(
    private val dao: WallpaperDao,
    private val userRepository: UserRepository,
) : WallpaperRepository {
    override suspend fun getWallpapers(): List<WallpaperEntity> {
        val user = userRepository.getCurrentAuthenticatedUser() ?: return listOf()
        return dao.getAllFromAuthor(user.id).map(DbWallpaperEntity::toEntity)
    }

    override suspend fun addWallpaper(request: AddWallpaperRequest): AddWallpaperResponse {
        val user = userRepository.getCurrentAuthenticatedUser()
            ?: return AddWallpaperResponse.Failure
        val entity = DbWallpaperEntity(
            name = request.name,
            author = user.id,
            description = request.description,
            pictureUri = request.pictureUri,
        )
        dao.add(entity)
        return AddWallpaperResponse.Success
    }

    override suspend fun deleteWallpaperById(id: UUID): DeleteWallpaperResponse {
        val deletedCount = dao.deleteById(id)
        if (deletedCount == 0) return DeleteWallpaperResponse.IdNotFound
        return DeleteWallpaperResponse.Success
    }
}