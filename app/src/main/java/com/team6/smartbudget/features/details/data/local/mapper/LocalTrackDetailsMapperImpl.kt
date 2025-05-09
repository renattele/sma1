package com.team6.smartbudget.features.details.data.local.mapper

import com.team6.smartbudget.core.domain.DataSourceType
import com.team6.smartbudget.features.details.data.local.LocalTrackDetailsDto
import com.team6.smartbudget.features.details.domain.TrackDetailsEntity
import javax.inject.Inject

class LocalTrackDetailsMapperImpl @Inject constructor() : LocalTrackDetailsMapper {
    override fun toDto(entity: TrackDetailsEntity): LocalTrackDetailsDto {
        return LocalTrackDetailsDto(
            title = entity.title,
            artist = entity.artist,
            thumbnailUrl = entity.thumbnailUrl,
            summary = entity.summary,
            published = entity.published,
            accessedCount = 0
        )
    }

    override fun toDomain(dto: LocalTrackDetailsDto): TrackDetailsEntity {
        return TrackDetailsEntity(
            title = dto.title,
            artist = dto.artist,
            thumbnailUrl = dto.thumbnailUrl,
            summary = dto.summary,
            published = dto.published,
            sourceType = DataSourceType.Local
        )
    }
}